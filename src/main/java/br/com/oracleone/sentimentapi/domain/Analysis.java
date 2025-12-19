package br.com.oracleone.sentimentapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "AnalysisTopic")
@Table(name = "analysis")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;
    private String analyzed;
    private String forecast;
    private Float probability;

    public Analysis(AnalysisInsertDTO analysis) {
        this.dateCreated = analysis.dateCreated();
        this.analyzed = analysis.analyzed();
        this.forecast = analysis.forecast();
        this.probability = analysis.probability();
    }

    public void updateAnalysis(AnalysisInsertDTO analysis) {
        this.dateCreated = analysis.dateCreated();
        this.analyzed = analysis.analyzed();
        this.forecast = analysis.forecast();
        this.probability = analysis.probability();
    }

    public Long getId() { return this.id; }
    public LocalDateTime getDateCreated() { return this.dateCreated; }
    public String getAnalyzed() { return this.analyzed; }
    public String getForecast() { return this.forecast; }
    public Float getProbability() { return this.probability; }
}
