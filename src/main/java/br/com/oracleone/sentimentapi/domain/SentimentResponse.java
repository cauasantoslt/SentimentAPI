package br.com.oracleone.sentimentapi.domain;

public record SentimentResponse(
        String sentiment,
        double probability
) {}