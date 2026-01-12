package br.com.oracleone.sentimentapi.domain;

public record StatsResponse(
        long total,
        long used,
        long positive,
        long negative,
        long neutral,
        double positivePercentage,
        double negativePercentage,
        double neutralPercentage
) {}
