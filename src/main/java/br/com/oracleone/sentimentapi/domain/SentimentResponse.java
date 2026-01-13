package br.com.oracleone.sentimentapi.domain;

import java.util.List;

public record SentimentResponse(
        String sentiment,
        double probability,
        List<String> topFeatures
) {
}