package br.com.oracleone.sentimentapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Errors")
@Table(name = "errors")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Errors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;
    @Column(name = "log_level", nullable = false)
    private String logLevel;
    @Column(name = "class_name", nullable = false)
    private String className;
    @Column(name = "error_msg", nullable = false)
    private String error;
    @Column(name = "stack_trace", nullable = false)
    private String stackTrace;

    public Errors(ErrorsDTO errors){
        this.dateCreated = errors.dateCreated();
        this.logLevel = errors.logLevel();
        this.className = errors.className();
        this.error = errors.error();
        this.stackTrace = errors.stackTrace();
    }

    public Long getId() { return this.id; }
    public LocalDateTime getDateCreated() { return this.dateCreated; }
    public String getLogLevel() { return this.logLevel; }
    public String getClassName() { return this.className; }
    public String getError() { return this.error; }
    public String getStackTrace() { return this.stackTrace; }
}
