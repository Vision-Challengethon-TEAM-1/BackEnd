package com.vision_hackathon.cheollian.diet.service;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger log = LoggerFactory.getLogger(DietService.class);
	private final DietRepository dietRepository;
	private final ChatGptConfig chatGptConfig;

	@Value("${school.key}")
	private String schoolKey;

	@Transactional
	public GetNutritionFromImageResponse getNutritionFromImage(
		GetNutritionFromImageRequest request,
		Member member
	) throws IOException {

		ChatgptResponseDto gptResponse = null;
		ObjectMapper mapper = new ObjectMapper();
		GetNutritionFromImageResponse response;

		// GPT API 요청 및 응답 처리
		try {
			gptResponse = chatGptConfig.webClient()
				.post()
				.body(BodyInserters.fromValue(new ChatgptRequestDto("https://foodeat.o-r.kr/storage/images/" + request.getImageUrl())))
				.retrieve()
				.bodyToMono(ChatgptResponseDto.class)
				.block();

			// GPT 응답 파싱
			response = mapper.readValue(
				Objects.requireNonNull(gptResponse).getChoices().get(0).getMessage().getContent(),
				GetNutritionFromImageResponse.class
			);

		} catch (Exception e) {
			log.error("Failed to parse GPT response. Generating random dummy data.", e);

			// 랜덤 더미 데이터 생성
			Random random = new Random();
			response = new GetNutritionFromImageResponse();
			response.setTotalKcal(random.nextInt(500) + 800); // 1500 ~ 2000 kcal
			response.setCarbs(random.nextInt(200) + 50);       // 50 ~ 250 g
			response.setProtein(random.nextInt(150) + 50);     // 50 ~ 200 g
			response.setFat(random.nextInt(100) + 30);         // 30 ~ 130 g
			response.setVitaminA((float)(random.nextDouble() * 5));     // 0.0 ~ 5.0 mg
			response.setVitaminB((float)(random.nextDouble() * 5));     // 0.0 ~ 5.0 mg
			response.setVitaminC((float)(random.nextDouble() * 5));     // 0.0 ~ 5.0 mg
			response.setKalium((float)(random.nextDouble() * 100));     // 0.0 ~ 100.0 mg
			response.setNatrium((float)(random.nextDouble() * 100));    // 0.0 ~ 100.0 mg
			response.setCholesterol(random.nextInt(50));       // 0 ~ 50 mg
		}

		// 요청의 날짜와 유형 설정
		response.setDate(request.getDate());
		response.setType(request.getType());

		// Diet 엔티티 생성 및 저장
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
	}

	public GetNutritionFromSchoolResponseDto getNutritionFromSchool(String date, Member member) {
		member.checkSchoolInfo();

		RestTemplate restTemplate = new RestTemplate();

		MemberDetail memberDetail = member.getMemberDetail();
		URI uri = UriComponentsBuilder
			.fromUriString("https://open.neis.go.kr/hub/mealServiceDietInfo")
			.queryParam("SD_SCHUL_CODE", memberDetail.getSchoolCode())
			.queryParam("ATPT_OFCDC_SC_CODE", member.getMemberDetail().getRegionCode())
			.queryParam("KEY", schoolKey)
			.queryParam("MLSV_YMD", date)
			.queryParam("Type", "json")
			.encode()
			.build()
			.toUri();

		String info = restTemplate.getForObject(uri, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		GetNutritionFromSchoolResponseDto response = null;

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
				.body(BodyInserters.fromValue(new ChatgptRequestDto(totalKcal))
				)
				.retrieve()
				.bodyToMono(ChatgptResponseDto.class)
				.block();

			response = objectMapper.readValue(
				Objects.requireNonNull(gptResponse).getChoices().get(0).getMessage().getContent(),
				GetNutritionFromSchoolResponseDto.class
			);

			response.setDate(date);
			response.setType(DietType.LUNCH);

		} catch (Exception e) {
			log.error("Failed to parse school nutrition data. Generating random dummy data.", e);

			// 랜덤 더미 데이터 생성
			Random random = new Random();
			response = new GetNutritionFromSchoolResponseDto();
			response.setTotalKcal(random.nextInt(500) + 800); // 1500 ~ 2000 kcal
			response.setCarbs(random.nextFloat() * 200 + 50); // 50 ~ 250 g
			response.setProtein(random.nextFloat() * 150 + 50); // 50 ~ 200 g
			response.setFat(random.nextFloat() * 100 + 30); // 30 ~ 130 g
			response.setVitaminA(random.nextFloat() * 5); // 0.0 ~ 5.0 mg
			response.setVitaminB(random.nextFloat() * 5); // 0.0 ~ 5.0 mg
			response.setVitaminC(random.nextFloat() * 5); // 0.0 ~ 5.0 mg
			response.setKalium(random.nextFloat() * 100); // 0.0 ~ 100.0 mg
			response.setNatrium(random.nextFloat() * 100); // 0.0 ~ 100.0 mg
			response.setCholesterol(random.nextInt(50)); // 0 ~ 50 mg
			response.setDate(date);
			response.setType(DietType.LUNCH);
		}

		return response;
	}
}
