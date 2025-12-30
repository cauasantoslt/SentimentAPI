package br.com.oracleone.sentimentapi.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta da análise de sentimento")
public record SentimentResponse(
        @Schema(description = "Texto analisado", example = "O atendimento foi ótimo")
        String text,
        @Schema(description = "Classificação", example = "Positivo")
        String sentiment,
        @Schema(description = "Probabilidade (0-1)", example = "0.87")
        double probability
) {}