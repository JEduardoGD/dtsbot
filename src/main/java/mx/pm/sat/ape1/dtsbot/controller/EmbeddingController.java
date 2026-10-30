package mx.pm.sat.ape1.dtsbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/embeddings")
@RequiredArgsConstructor
public class EmbeddingController {


    @GetMapping("/generate")
    public String generate(@RequestParam String message) {
        
        return null;
    }
}
