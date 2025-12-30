package br.com.oracleone.sentimentapi.controller;

import br.com.oracleone.sentimentapi.domain.SentimentRequest;
import br.com.oracleone.sentimentapi.domain.SentimentResponse;
import br.com.oracleone.sentimentapi.model.Analysis;
import br.com.oracleone.sentimentapi.repository.AnalysisRepository;
import br.com.oracleone.sentimentapi.service.SentimentAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    // --- ENDPOINT 1: ANALISAR (POST) ---
    @PostMapping
    @Operation(summary = "Analisa o sentimento de um texto") // Swagger em PT-BR
    public ResponseEntity<SentimentResponse> analyze(@RequestBody @Valid SentimentRequest request) throws Exception {

        // 1. Chama a IA
        var result = service.predict(request.text());

        // 2. Salva no Banco
        Analysis analysis = new Analysis(request.text(), result.label(), result.probability());
        repository.save(analysis);

        // 3. Retorna JSON
        return ResponseEntity.ok(
                new SentimentResponse(
                        request.text(),
                        result.label(),
                        result.probability()
                )
        );
    }

    // --- ENDPOINT 2: HISTÓRICO (GET) ---
    @GetMapping("/history")
    @Operation(summary = "Lista o histórico de análises (Paginado)")
    public ResponseEntity<Page<SentimentResponse>> listHistory(@PageableDefault(size = 10, sort = "id") Pageable pageable) {

        // Busca no banco paginado e converte para o DTO de resposta padrão
        Page<SentimentResponse> history = repository.findAll(pageable)
                .map(item -> new SentimentResponse(
                        item.getAnalyzedText(),
                        item.getForecast(),
                        item.getProbability()
                ));
        return ResponseEntity.ok(history);
    }
}