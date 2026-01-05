package br.com.oracleone.sentimentapi.controller;

import br.com.oracleone.sentimentapi.domain.HistoryResponse;
import br.com.oracleone.sentimentapi.domain.SentimentRequest;
import br.com.oracleone.sentimentapi.domain.SentimentResponse;
import br.com.oracleone.sentimentapi.domain.StatsResponse;
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

import java.util.Comparator;
import java.util.List;

@CrossOrigin(origins = "*")
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
                        result.label(),
                        result.probability()
                )
        );
    }

    // --- ENDPOINT 2: HISTÓRICO (GET) ---
    @GetMapping("/history")
    @Operation(summary = "Lista o histórico de análises (Paginado)")
    public ResponseEntity<Page<HistoryResponse>> listHistory(@PageableDefault(size = 4, sort = "id") Pageable pageable) {

        // Busca no banco paginado e converte para o DTO de resposta padrão
        Page<HistoryResponse> history = repository.findAll(pageable)
                .map(item -> new HistoryResponse(
                        item.getAnalyzedText(),
                        item.getForecast(),
                        item.getProbability()
                ));

        return ResponseEntity.ok(history);
    }

    // --- ENDPOINT 3: ESTATÍSTICA (GET) ---
    @GetMapping("/stats")
    @Operation(summary = "Estatísticas de sentimento dos últimos X comentários")
    public ResponseEntity<StatsResponse> getStats(
            @RequestParam(defaultValue = "20") int limit
    ) {

        List<Analysis> allAnalyses = repository.findAll();

        if (allAnalyses.isEmpty()) {
            return ResponseEntity.ok(
                    new StatsResponse(0, 0, 0, 0, 0, 0, 0)
            );
        }

        List<Analysis> lastAnalyses = allAnalyses.stream()
                .sorted(Comparator.comparing(Analysis::getId).reversed())
                .limit(limit)
                .toList();

        long total = lastAnalyses.size();

        long positive = lastAnalyses.stream()
                .filter(a -> "POSITIVO".equalsIgnoreCase(a.getForecast()))
                .count();

        long negative = lastAnalyses.stream()
                .filter(a -> "NEGATIVO".equalsIgnoreCase(a.getForecast()))
                .count();

        long neutral = lastAnalyses.stream()
                .filter(a -> "NEUTRO".equalsIgnoreCase(a.getForecast()))
                .count();

        double positivePercentage = (positive * 100.0) / total;
        double negativePercentage = (negative * 100.0) / total;
        double neutralPercentage = (neutral * 100.0) / total;

        StatsResponse response = new StatsResponse(
                total,
                positive,
                negative,
                neutral,
                positivePercentage,
                negativePercentage,
                neutralPercentage
        );

        return ResponseEntity.ok(response);
    }
}
