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

        // Verificar com DS qual é o imput
        Map<String, OnnxTensor> inputs = Collections.singletonMap("text_input", tensor);

        try (OrtSession.Result results = session.run(inputs)) {
            // 1. Pegar Label (Positivo/Negativo)
            long[] labels = (long[]) results.get(0).getValue();
            long predictedLabel = labels[0];

            String resultado;
            // Mapeamento baseado no treino do DS (provavelmente tem que ajustar)
            switch ((int) predictedLabel) {
                case 0: resultado = "Negativo"; break;
                case 1: resultado = "Positivo"; break;
                case 2: resultado = "Neutro"; break;
                default: resultado = "Desconhecido";
            }

            // 2. Pegar Probabilidade
            List<OnnxMap> probabilitySequence = (List<OnnxMap>) results.get(1).getValue();
            OnnxMap onnxMap = probabilitySequence.get(0);
            Map<Long, Float> probMap = (Map<Long, Float>) onnxMap.getValue();

            // Pega a probabilidade da classe escolhida
            float probabilidade = probMap.getOrDefault(predictedLabel, 0.0f);

            return new PredictionResult(resultado, (double) probabilidade);
        }
    }

    // Classe interna simples para passar os dados
    public record PredictionResult(String label, double probability) {}
}