package br.com.oracleone.sentimentapi.service;

import ai.onnxruntime.*;
import br.com.oracleone.sentimentapi.model.Analysis;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SentimentAnalysisService {

    private final OrtEnvironment env;
    private final OrtSession session;

    public SentimentAnalysisService() throws Exception {
        this.env = OrtEnvironment.getEnvironment();

        ClassPathResource resource = new ClassPathResource("sentiment_model_multilang.onnx");

        Path tempFile = Files.createTempFile("sentiment-model-", ".onnx");

        try (InputStream is = resource.getInputStream()) {
            Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }

        tempFile.toFile().deleteOnExit();

        this.session = env.createSession(tempFile.toString());
    }

    public PredictionResult predict(String text) throws Exception {
        String[][] sourceArray = new String[1][1];
        sourceArray[0][0] = text;

        OnnxTensor tensor = OnnxTensor.createTensor(env, sourceArray);

        Map<String, OnnxTensor> inputs = Collections.singletonMap("text_input", tensor);

        try (OrtSession.Result results = session.run(inputs)) {
            var labelResult = results.get("output_label").isPresent() ?
                    results.get("output_label").get() : results.get(0);

            long[] labels = (long[]) results.get(0).getValue();
            long predictedLabel = labels[0];

            String sentiment;
            switch ((int) predictedLabel) {
                case 0: sentiment = "Negativo"; break;
                case 1: sentiment = "Neutro"; break;
                case 2: sentiment = "Positivo"; break;
                default: sentiment = "Desconhecido";
            }

            var probResult = results.get("output_probability").isPresent() ?
                    results.get("output_probability").get() : results.get(1);

            @SuppressWarnings("unchecked")
            List<OnnxMap> probabilitySequence = (List<OnnxMap>) probResult.getValue();
            OnnxMap onnxMap = probabilitySequence.get(0);

            @SuppressWarnings("unchecked")
            Map<Long, Float> probMap = (Map<Long, Float>) onnxMap.getValue();

            float probability = probMap.getOrDefault(predictedLabel, 0.0f);

            return new PredictionResult(sentiment, (double) probability);
        }
    }

    public List<Analysis> processBatch(MultipartFile file) throws Exception {
        List<Analysis> analyses = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                // Tenta identificar e pular cabeçalho (ex: coluna "texto" ou "review")
                if (isFirstLine) {
                    isFirstLine = false;
                    String lowerLine = line.toLowerCase();
                    if (lowerLine.contains("text") || lowerLine.contains("comentario") || lowerLine.contains("review")) {
                        continue;
                    }
                }

                String text = line.trim();
                if (text.startsWith("\"") && text.endsWith("\"")) {
                    text = text.substring(1, text.length() - 1);
                }

                if (!text.isEmpty()) {
                    PredictionResult result = predict(text);

                    analyses.add(new Analysis(text, result.label(), result.probability()));
                }
            }
        }
        return analyses;
    }

    public record PredictionResult(String label, double probability) {}
}