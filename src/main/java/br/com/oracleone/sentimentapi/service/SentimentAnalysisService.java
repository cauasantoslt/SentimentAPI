package br.com.oracleone.sentimentapi.service;

import ai.onnxruntime.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SentimentAnalysisService {

    private final OrtEnvironment env;
    private final OrtSession session;

    public SentimentAnalysisService() throws Exception {
        this.env = OrtEnvironment.getEnvironment();

        // CORREÇÃO PARA DOCKER/JAR:
        // Não usar Paths.get(URI), usar getResourceAsStream + readAllBytes
        try (InputStream modelStream = getClass().getClassLoader().getResourceAsStream("sentiment_model_multilang.onnx")) {
            if (modelStream == null) {
                throw new RuntimeException("Arquivo 'sentiment_model_multilang.onnx' não encontrado em resources!");
            }
            byte[] modelArray = modelStream.readAllBytes();
            this.session = env.createSession(modelArray);
        }
    }

    // Retorno agora é um objeto contendo Label e Probabilidade
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

            // 2. Pegar Probabilidade
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

    public record PredictionResult(String label, double probability) {}
}