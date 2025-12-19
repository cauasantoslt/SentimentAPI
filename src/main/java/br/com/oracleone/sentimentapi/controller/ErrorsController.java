package br.com.oracleone.sentimentapi.controller;

import br.com.oracleone.sentimentapi.domain.Errors;
import br.com.oracleone.sentimentapi.domain.ErrorsDTO;
import br.com.oracleone.sentimentapi.repository.ErrorsRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/errors")
public class ErrorsController {
    @Autowired
    private ErrorsRepo errorsRepo;

    @GetMapping("/{id}")
    public ResponseEntity searchErrors(@PathVariable Long id){
        var errors = errorsRepo.getReferenceById(id);
        return ResponseEntity.ok(new ErrorsDTO(errors));
    }

    @GetMapping
    public ResponseEntity<Page<ErrorsDTO>> listErrors(@PageableDefault(sort = {"id"}) Pageable pageable){
        var pag = errorsRepo.findAll(pageable).map(ErrorsDTO::new);
        return ResponseEntity.ok(pag);
    }

    @PostMapping
    public Errors newErrors(@RequestBody @Valid Errors a){ return errorsRepo.save(a); }

    @PutMapping()
    public Errors editErrors(@RequestBody @Valid Errors a){ return errorsRepo.save(a); }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteErrors(@PathVariable Long id){
        errorsRepo.deleteById(id);
    }
}