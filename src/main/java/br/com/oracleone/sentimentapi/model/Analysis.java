package br.com.oracleone.sentimentapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "analysis")
@Data
@NoArgsConstructor
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated = LocalDateTime.now();

    @Column(name = "analyzed_text", columnDefinition = "TEXT")
    private String analyzedText;

    private String forecast;
    private double probability;

    public Analysis(String analyzedText, String forecast, double probability) {
        this.analyzedText = analyzedText;
        this.forecast = forecast;
        this.probability = probability;
    }
}