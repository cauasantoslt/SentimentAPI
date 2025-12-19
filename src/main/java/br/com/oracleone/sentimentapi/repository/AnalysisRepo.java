package br.com.oracleone.sentimentapi.repository;

import br.com.oracleone.sentimentapi.domain.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepo extends JpaRepository<Analysis, Long> {
}
