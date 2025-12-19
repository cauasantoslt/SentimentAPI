package br.com.oracleone.sentimentapi.domain;

import java.time.LocalDateTime;

public record AnalysisReadDTO(
        Long id,
        LocalDateTime dateCreated,
        String analyzed,
        String forecast,
        Float probability
        ) {

    public AnalysisReadDTO(Analysis analysis){
        this(   analysis.getId(),
                analysis.getDateCreated(),
                analysis.getAnalyzed(),
                analysis.getForecast(),
                analysis.getProbability());
    }
}
