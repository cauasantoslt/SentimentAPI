package br.com.oracleone.sentimentapi.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta da análise de sentimento")
public record SentimentResponse(
        @Schema(description = "Classificação", example = "Positivo")
        String previsao,
        @Schema(description = "Probabilidade (0-1)", example = "0.87")
        double probabilidade
) {}