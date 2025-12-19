package br.com.oracleone.sentimentapi.service;

public class ExceptionsValid extends RuntimeException {
    public ExceptionsValid(String mensagem) {
        super(mensagem);
    }
}