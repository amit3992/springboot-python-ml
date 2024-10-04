package com.playerdb.playerservice.service.chat;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClientService.class);
    private final OpenAiService openAiService;

    @Value("${chat.openai.model}")
    private String model;

    public CompletionResult complete(String prompt) {
        return complete(toRequest(prompt));
    }

    public CompletionRequest toRequest(String prompt) {
        return CompletionRequest.builder().maxTokens(1000).model(model).prompt(prompt)
                .echo(true).user("testing").n(1).build();
    }

    public CompletionResult complete(CompletionRequest completionRequest) {
        LOGGER.info("Creating a completion: {}", completionRequest.toString());
        return this.openAiService.createCompletion(completionRequest);
    }

    public ChatCompletionResult complete(ChatCompletionRequest request) {
        LOGGER.info("Creating chat completion: {}", request);
        return this.openAiService.createChatCompletion(request);
    }

    public ChatCompletionRequest toChatRequest(String prompt) {
        return ChatCompletionRequest.builder().model(model).n(1).maxTokens(1000).user("assistant")
                .messages(List.of(new ChatMessage("user", prompt))).build();
    }
}
