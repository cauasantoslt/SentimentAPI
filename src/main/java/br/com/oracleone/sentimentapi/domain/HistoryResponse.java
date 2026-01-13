package br.com.oracleone.sentimentapi.domain;

import java.util.List;

public record HistoryResponse(
        String analyzedText,
        String forecast,
        double probability,
        List<String> topFeatures
) {}
