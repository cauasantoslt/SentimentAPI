package br.com.oracleone.sentimentapi.controller;

import br.com.oracleone.sentimentapi.domain.Analysis;
import br.com.oracleone.sentimentapi.domain.AnalysisInsertDTO;
import br.com.oracleone.sentimentapi.domain.AnalysisReadDTO;
import br.com.oracleone.sentimentapi.repository.AnalysisRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Autowired
    private AnalysisRepo analysisRepo;

    @PostMapping
    @Transactional
    public ResponseEntity createAnalysis(@RequestBody @Valid AnalysisInsertDTO analysisDTO, UriComponentsBuilder builder){
        var analysis = new Analysis(analysisDTO);
        analysisRepo.save(analysis);

        var uri = builder.path("/code/{id}").buildAndExpand(analysis.getId()).toUri();
        return ResponseEntity.created(uri).body((new AnalysisReadDTO(analysis)));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchAnalysis(@PathVariable Long id){
        var analysis = analysisRepo.getReferenceById(id);
        return ResponseEntity.ok(new AnalysisReadDTO(analysis));
    }

    @GetMapping
    public ResponseEntity<Page<AnalysisReadDTO>> listAnalysis(@PageableDefault(sort = {"id"}) Pageable pageable){
        var pagina = analysisRepo.findAll(pageable).map(AnalysisReadDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateAnalysis(@PathVariable Long id, @RequestBody @Valid AnalysisInsertDTO analysisDTO) {

        var analysis = analysisRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Analise não encontrada"));

        analysis.updateAnalysis(analysisDTO);
        analysisRepo.save(analysis);

        return ResponseEntity.ok(new AnalysisReadDTO(analysis));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
        if (!analysisRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        analysisRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
