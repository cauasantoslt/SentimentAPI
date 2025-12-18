package br.com.oracleone.sentimentapi.controller;

import br.com.oracleone.sentimentapi.domain.SentimentRequest;
import br.com.oracleone.sentimentapi.domain.SentimentResponse;
import br.com.oracleone.sentimentapi.model.Analysis;
import br.com.oracleone.sentimentapi.repository.AnalysisRepository;
import br.com.oracleone.sentimentapi.service.SentimentAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sentiment")
public class SentimentController {

    private final SentimentAnalysisService service;
    private final AnalysisRepository repository;

    public SentimentController(SentimentAnalysisService service, AnalysisRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    @Operation(summary = "Analisa o sentimento de um texto")
    public ResponseEntity<SentimentResponse> analisar(@RequestBody @Valid SentimentRequest request) {
        try {
            // 1. Chama o Serviço de IA
            var resultado = service.predict(request.text());

            // 2. Salva no Banco (H2 ou Postgres)
            Analysis analysis = new Analysis(request.text(), resultado.label(), resultado.probability());
            repository.save(analysis);

            // 3. Retorna pro usuário
            return ResponseEntity.ok(
                    new SentimentResponse(resultado.label(), resultado.probability())
            );

        } catch (Exception e) {
            // Log do erro real no console
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}