package br.com.oracleone.sentimentapi.controller;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/sentiment")
public class SentimentController {

    @PostMapping
    public String analisar(@RequestBody Map<String, String> body) {
        return body.get("text");

    }
}
