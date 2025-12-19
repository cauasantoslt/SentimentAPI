package br.com.oracleone.sentimentapi.repository;

import br.com.oracleone.sentimentapi.domain.Errors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorsRepo extends JpaRepository<Errors, Long> {
}
