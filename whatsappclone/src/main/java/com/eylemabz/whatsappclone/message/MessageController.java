package com.eylemabz.whatsappclone.message;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ap/v1/messages")
@Tag(name = "Message")
public class MessageController {

    private final MessageService messageService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMessage(@RequestBody MessageRequest message){
        messageService.saveMessage(message);
    }

    @PostMapping(value = "/upload-media",consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMedia(
            @RequestParam("chat-id") String chatId,
            @Parameter()
            @RequestParam("file")MultipartFile file,
            Authentication authentication
            ){
        messageService.uploadMediaMessage(chatId,file,authentication);
    }

    @GetMapping("/chat/{chat-id}")
    public ResponseEntity<List<MessageResponse>> getMessage(
            @PathVariable("chat-id") String chatId
    ){
        return ResponseEntity.ok(messageService.findChatMessages(chatId));
    }
}
