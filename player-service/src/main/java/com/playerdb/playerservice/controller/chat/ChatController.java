package com.playerdb.playerservice.controller.chat;


import com.playerdb.playerservice.model.chat.ChatPrompt;
import com.playerdb.playerservice.service.chat.ChatClientService;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
@RequestMapping(value = "v1/chat", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    private final ChatClientService chatClientService;

    @PostMapping
    public @ResponseBody CompletionResult chat(@RequestBody ChatPrompt promptDto) {
        LOGGER.info("Generating response for  request {}", promptDto.toString());
        var result = chatClientService.complete(promptDto.getPrompt());
        result.getChoices().forEach(choice -> LOGGER.info("Result choice: {}", choice.toString()));
        return result;
    }

    @PostMapping("/conversation")
    public @ResponseBody ChatCompletionResult chatConversation(@RequestBody ChatPrompt promptDto) {
        LOGGER.info("Generating response for  request {}", promptDto.toString());
        ChatCompletionRequest chatCompletionRequest = chatClientService.toChatRequest(promptDto.getPrompt());
        var result = chatClientService.complete(chatCompletionRequest);
        result.getChoices().forEach(choice -> LOGGER.info("Result-choice={}", choice.toString()));
        return result;
    }
}
