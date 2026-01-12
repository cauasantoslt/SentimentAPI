package br.com.oracleone.sentimentapi.controller;

import br.com.oracleone.sentimentapi.domain.HistoryResponse;
import br.com.oracleone.sentimentapi.domain.SentimentRequest;
import br.com.oracleone.sentimentapi.domain.SentimentResponse;
import br.com.oracleone.sentimentapi.domain.StatsResponse;
import br.com.oracleone.sentimentapi.model.Analysis;
import br.com.oracleone.sentimentapi.repository.AnalysisRepository;
import br.com.oracleone.sentimentapi.service.SentimentAnalysisService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping
    public ResponseEntity<SentimentResponse> analyze(@RequestBody @Valid SentimentRequest request) throws Exception {
        var result = service.predict(request.text());

        Analysis analysis = new Analysis(request.text(), result.label(), result.probability());
        repository.save(analysis);

        return ResponseEntity.ok(
                new SentimentResponse(
                        result.label(),
                        result.probability(),
                        result.topFeatures()));
    }

    @PostMapping(value = "/batch", consumes = "multipart/form-data")
    public ResponseEntity<List<HistoryResponse>> analyzeBatch(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "header", defaultValue = "true") boolean header) throws Exception {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo não pode estar vazio.");
        }

        List<Analysis> entities = service.processBatch(file, header);

        repository.saveAll(entities);

        List<HistoryResponse> response = entities.stream()
                .map(a -> new HistoryResponse(
                        a.getAnalyzedText(),
                        a.getForecast(),
                        a.getProbability(),
                        service.extractTopFeatures(a.getAnalyzedText(), a.getForecast())))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<Page<HistoryResponse>> listHistory(@PageableDefault(size = 4, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<HistoryResponse> history = repository.findAll(pageable)
                .map(item -> new HistoryResponse(
                        item.getAnalyzedText(),
                        item.getForecast(),
                        item.getProbability(),
                        service.extractTopFeatures(item.getAnalyzedText(), item.getForecast())));

        return ResponseEntity.ok(history);
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats(@RequestParam(defaultValue = "20") int limit) {
        List<Analysis> allAnalyses = repository.findAll();

        if (allAnalyses.isEmpty()) {
            return ResponseEntity.ok(new StatsResponse(0, 0, 0, 0, 0, 0, 0, 0));
        }

        List<Analysis> lastAnalyses = allAnalyses.stream()
                .sorted(Comparator.comparing(Analysis::getId).reversed())
                .limit(limit)
                .toList();

        long total = allAnalyses.size();
        long used = lastAnalyses.size();
        long positive = lastAnalyses.stream().filter(a -> "Positivo".equalsIgnoreCase(a.getForecast())).count();
        long negative = lastAnalyses.stream().filter(a -> "Negativo".equalsIgnoreCase(a.getForecast())).count();
        long neutral = lastAnalyses.stream().filter(a -> "Neutro".equalsIgnoreCase(a.getForecast())).count();

        double posPct = used > 0 ? (positive * 100.0) / used : 0;
        double negPct = used > 0 ? (negative * 100.0) / used : 0;
        double neuPct = used > 0 ? (neutral * 100.0) / used : 0;

        return ResponseEntity.ok(new StatsResponse(total, used, positive, negative, neutral, posPct, negPct, neuPct));
    }
}