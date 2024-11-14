package com.vision_hackathon.cheollian.util.chatgpt;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatgptRequestDto implements Serializable {
    private List<Message> messages = new ArrayList<>();

    private String model = "gpt-4o";

    public ChatgptRequestDto(String imageUrl){
        this.messages.add(
                new ImageMessage("user",
                        List.of(
                                new TextContent("text", Prompt.FOOD_ANALYSIS),
                                new ImageContent("image_url", new ImageUrl(imageUrl))
                        ))
        );
    }



}
