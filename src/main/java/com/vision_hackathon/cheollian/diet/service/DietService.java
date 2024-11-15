package com.vision_hackathon.cheollian.diet.service;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vision_hackathon.cheollian.config.ChatGptConfig;
import com.vision_hackathon.cheollian.diet.dto.GetNutritionFromImageRequest;
import com.vision_hackathon.cheollian.diet.dto.GetNutritionFromImageResponse;
import com.vision_hackathon.cheollian.diet.dto.GetNutritionFromSchoolResponseDto;
import com.vision_hackathon.cheollian.diet.entity.Diet;
import com.vision_hackathon.cheollian.diet.entity.DietType;
import com.vision_hackathon.cheollian.diet.persistence.DietRepository;
import com.vision_hackathon.cheollian.member.entity.Member;
import com.vision_hackathon.cheollian.member.entity.MemberDetail;
import com.vision_hackathon.cheollian.util.chatgpt.ChatgptRequestDto;
import com.vision_hackathon.cheollian.util.chatgpt.ChatgptResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DietService {
	private final DietRepository dietRepository;
	private final ChatGptConfig chatGptConfig;

	@Value("${school.key}")
	private String schoolKey;

	@Transactional
	public GetNutritionFromImageResponse getNutritionFromImage(
			GetNutritionFromImageRequest request,
			Member member
		) throws IOException {
		// String uploadedImage = cloudStorageService.uploadObject(image);

		ChatgptResponseDto gptResponse = chatGptConfig.webClient()
				.post()
				.body(BodyInserters.fromValue(new ChatgptRequestDto(request.getImageUrl())))
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
			response.setType(request.getType());

			Diet diet = Diet.builder()
					.image(request.getImageUrl())
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

	public GetNutritionFromSchoolResponseDto getNutritionFromSchool(String date, Member member) {
		member.checkSchoolInfo();

		RestTemplate restTemplate = new RestTemplate();

		MemberDetail memberDetail = member.getMemberDetail();
		//FIX: 행정구역 코드 하드코딩 해놓음 수정 필요
		URI uri = UriComponentsBuilder
				.fromUriString("https://open.neis.go.kr/hub/mealServiceDietInfo")
				.queryParam("SD_SCHUL_CODE", memberDetail.getSchoolCode())
				.queryParam("ATPT_OFCDC_SC_CODE", "D10")
				.queryParam("KEY", schoolKey)
				.queryParam("MLSV_YMD", date)
				.queryParam("Type", "json")
				.encode()
				.build()
				.toUri();


		String info = restTemplate.getForObject(uri, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				JsonNode rootNode = objectMapper.readTree(info);

				Map<String, String> calInfoMap = new HashMap<>();

				String totalKcal = "";

				JsonNode mealServiceDietInfoNode = rootNode.path("mealServiceDietInfo");
				for (JsonNode node : mealServiceDietInfoNode) {
					JsonNode rowNode = node.path("row");

					// row 배열 탐색
					if (rowNode.isArray()) {
						for (JsonNode rowElement : rowNode) {
							String calInfo = rowElement.path("CAL_INFO").asText();
							totalKcal = totalKcal.concat(calInfo);
						}
					}
				}

				ChatgptResponseDto gptResponse = chatGptConfig.webClient()
						.post()
						.body(BodyInserters.fromValue(new ChatgptRequestDto(totalKcal)))
						.retrieve()
						.bodyToMono(ChatgptResponseDto.class)
						.block();

				ObjectMapper mapper = new ObjectMapper();

				GetNutritionFromSchoolResponseDto response = mapper.readValue(
				gptResponse.getChoices().get(0).getMessage().getContent(),
						GetNutritionFromSchoolResponseDto.class);

				response.setDate(date);
				response.setType(DietType.LUNCH);

				return response;


			}catch (Exception e) {
				throw new RuntimeException(e);
			}

	}
}
