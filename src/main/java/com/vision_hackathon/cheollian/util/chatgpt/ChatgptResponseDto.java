package com.vision_hackathon.cheollian.util.chatgpt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChatgptResponseDto implements Serializable {
    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private List<Choice> choices;

    @Data
    public static class Choice{
        private Message message;
        private Integer index;
        @JsonProperty("finish_reason")
        private String finishReason;

        @Data
        public static class Message{
            private String role;
            private String content;
        }
    }
}
