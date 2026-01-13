package br.com.oracleone.sentimentapi.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SentimentRequest(
        @NotBlank(message = "Texto é obrigatório")
        @Size(min = 6, message = "Texto deve ter pelo menos 6 caracteres")
        String text
) {}