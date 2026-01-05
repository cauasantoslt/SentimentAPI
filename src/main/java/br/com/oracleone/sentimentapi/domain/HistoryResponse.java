package br.com.oracleone.sentimentapi.domain;

public record HistoryResponse(
        String analyzedText,
        String forecast,
        double probability
) {}
