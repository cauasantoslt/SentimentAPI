package br.com.oracleone.sentimentapi.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Requisição para análise de sentimento")
public record SentimentRequest(
        @NotBlank(message = "Texto é obrigatório")
        @Size(min = 10, message = "Texto deve ter pelo menos 10 caracteres")
        @Schema(description = "Comentário ou avaliação", example = "Adorei o atendimento!")
        String text
) {}