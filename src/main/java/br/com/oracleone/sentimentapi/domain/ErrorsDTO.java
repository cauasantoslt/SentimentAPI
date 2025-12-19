package br.com.oracleone.sentimentapi.domain;

import java.time.LocalDateTime;

public record ErrorsDTO (Long id, LocalDateTime dateCreated, String logLevel, String className, String error, String stackTrace) {

    public ErrorsDTO (Errors errors) {
        this(   errors.getId(),
                errors.getDateCreated(),
                errors.getLogLevel(),
                errors.getClassName(),
                errors.getError(),
                errors.getStackTrace());
    }
}