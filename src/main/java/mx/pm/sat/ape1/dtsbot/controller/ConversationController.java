package mx.pm.sat.ape1.dtsbot.controller;

import java.util.List;

import org.springframework.ai.chat.messages.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.dto.ResponseDTO;
import mx.pm.sat.ape1.dtsbot.service.DtsCallService;

@RestController
@RequestMapping("/conversation")
@RequiredArgsConstructor
public class ConversationController {

    private final DtsCallService dtsCallService;

    @GetMapping("/generateConversation")
    public ResponseEntity<ResponseDTO<Incident>> generate(@RequestParam String message) {

        List<Message> listContext = dtsCallService.loadContext();

        Incident textResponse = dtsCallService.proceessCall(listContext, message);

        return ResponseEntity.ok(new ResponseDTO<>(200, "SUCCESS", textResponse));
    }

}
