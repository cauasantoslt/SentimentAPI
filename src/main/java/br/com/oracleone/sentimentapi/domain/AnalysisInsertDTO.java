package br.com.oracleone.sentimentapi.domain;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record AnalysisInsertDTO(
    @NotBlank
    LocalDateTime dateCreated,
    @NotBlank
    String analyzed,
    @NotBlank
    String forecast,
    @NotBlank
    Float probability
){ }
