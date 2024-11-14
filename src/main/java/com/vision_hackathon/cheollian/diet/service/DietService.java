package com.vision_hackathon.cheollian.diet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vision_hackathon.cheollian.config.ChatGptConfig;
import com.vision_hackathon.cheollian.diet.dto.GetNutritionFromImageRequest;
import com.vision_hackathon.cheollian.diet.dto.GetNutritionFromImageResponse;
import com.vision_hackathon.cheollian.diet.entity.Diet;
import com.vision_hackathon.cheollian.member.entity.Member;
import com.vision_hackathon.cheollian.util.chatgpt.ChatgptRequestDto;
import com.vision_hackathon.cheollian.util.chatgpt.ChatgptResponseDto;
import com.vision_hackathon.cheollian.util.gcp.CloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vision_hackathon.cheollian.diet.persistence.DietRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DietService {
	@Autowired
	private final DietRepository dietRepository;
	@Autowired
	private final CloudStorageService cloudStorageService;
	@Autowired
	private final ChatGptConfig chatGptConfig;

	@Transactional
	public GetNutritionFromImageResponse getNutritionFromImage(
			GetNutritionFromImageRequest request,
			MultipartFile image,
			Member member
		) throws IOException {
		String uploadedImage = cloudStorageService.uploadObject(image);
		ChatgptResponseDto gptResponse = chatGptConfig.webClient()
				.post()
				.body(BodyInserters.fromValue(new ChatgptRequestDto(uploadedImage)))
				.retrieve()
				.bodyToMono(ChatgptResponseDto.class)
				.block();

		ObjectMapper mapper = new ObjectMapper();

		try {
			GetNutritionFromImageResponse response = mapper.readValue(
//				gptResponse.getChoices().get(0).getMessage().getContent(),
					"""
                            {
                                    "totalKcal" : 2400,
                                    "carbs": 100,
                                    "protein": 200,
                                    "fat": 100,
                                    "vitaminA" : 0.1,
                                    "vitaminB" : 0.1,
                                    "vitaminC" : 0.1,
                                    "kalium" : 70.7,
                                    "natrium" : 12.6,
                                    "cholesterol" : 15
                                }
                            """,
					GetNutritionFromImageResponse.class);

			response.setDate(request.getDate());

			Diet diet = Diet.builder()
					.image(uploadedImage)
					.date(request.getDate())
					.totalKcal(response.getTotalKcal())
					.carbs(response.getCarbs())
					.protein(response.getProtein())
					.fat(response.getFat())
					.natrium(response.getNatrium())
					.kalium(response.getKalium())
					.vitaminA(response.getVitaminA())
					.vitaminB(response.getVitaminB())
					.vitaminC(response.getVitaminC())
					.cholesterol(response.getCholesterol())
					.type(request.getType())
					.member(member)
					.build();

			dietRepository.save(diet);

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}




	}
}
