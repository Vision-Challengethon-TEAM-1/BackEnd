package com.vision_hackathon.cheollian.util.chatgpt;

public class Prompt {
    public static final String FOOD_ANALYSIS =
            """
                    Your job is to accurately analyze the nutrition by looking at the meal plan photos I send. 
                    Tell me the total calories (kcal), carbohydrates, proteins, fats, potassium, sodium, cholesterol, and vitamins (mg). 
                    Send it in the json format below and never use any other words than json. And write the food names in Korean.
                    
                    {
                      "total_kcal": 100,
                      "carbs": 100,
                      "protein": 200,
                      "fat": 100,
                      "vitaminA": 0.1,
                      "vitaminB": 0.1,
                      "vitaminC": 0.1,
                      "kalium": 70.7,
                      "natrium": 12.6,
                      "cholesterol": 15
                    }
                    """;

    public static final String SCHOOL_FOOD_ANALYSIS =
            """
                    From now on, you are the analyst who analyzes the total calories and nutrients from the food list I present. Please real Nutrient Information in json format below without "```json". Never say anything other than json.
                    
                    {
                        "totalKcal" : 2400,
                        "carbs": 100,
                        "protein": 200,
                        "fat": 100,
                        "vitaminA": 0.1,
                            "vitaminB": 0.2,
                            "vitaminC": 0.3,
                        "kalium": 70.7,
                        "natrium": 12.6,
                        "cholesterol": 15
                    }
                                    
                    Food Info : 
                    """;

    public static final String DAILY_ANALYSIS =
            """
                    In the following text, please look at the calorie intake for breakfast, lunch, and dinner for a day that I provide and give advice in Korean.
                   """;
}
