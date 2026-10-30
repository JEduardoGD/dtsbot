package mx.pm.sat.ape1.dtsbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mx.pm.sat.ape1.dtsbot.dto.ResponseDTO;

@RestController
@RequestMapping("/conversation")
@RequiredArgsConstructor
public class ConversationController {

    @GetMapping("/generateConversation")
    public ResponseEntity<ResponseDTO<String>> generate(@RequestParam String message) {
        return ResponseEntity.ok(new ResponseDTO<>(200, "SUCCESS", message));
    }

}
