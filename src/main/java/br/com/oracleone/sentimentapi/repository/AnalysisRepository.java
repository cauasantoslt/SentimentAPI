package br.com.oracleone.sentimentapi.repository;

import br.com.oracleone.sentimentapi.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
}