package br.com.oracleone.sentimentapi.service;

import ai.onnxruntime.OnnxMap;
import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SentimentAnalysisService {
    private final OrtEnvironment env;
    private final OrtSession session;

    public SentimentAnalysisService() throws Exception {
        this.env = OrtEnvironment.getEnvironment();
        //arquivo na raiz do resources
        String modelPath = Paths.get(getClass().getClassLoader().getResource("sentiment_model_multilang.onnx").toURI()).toString();
        this.session = env.createSession(modelPath, new OrtSession.SessionOptions());
    }

    public String predict(String text) throws Exception {
        String[][] sourceArray = new String[1][1];
        sourceArray[0][0] = text;
        OnnxTensor tensor = OnnxTensor.createTensor(env, sourceArray);
        Map<String, OnnxTensor> inputs = Collections.singletonMap("text_input", tensor);
        try (OrtSession.Result results = session.run(inputs)) {
            //saidas do modelo sao 2: System.out.println("Quantidade de outputs: " + results.size());

            //onnx_output_name: output_label, tensor: int64[?]
            long[] labels = (long[]) results.get(0).getValue();
            long predictedLabel = labels[0];
            System.out.println("Label previsto: " + labels[0]);
            String resultado;
            //Validar se isso mesmo no modelo q foi construido!!!!!!!!!!!
            switch ((int) predictedLabel) {
                case 0:
                    resultado = "Negativo";
                    break;
                case 1:
                    resultado = "Positivo";
                    break;
                default:
                    resultado = "Desconhecido";
            }
            System.out.println("Classificação final: " + resultado);

            //onnx_output_name: output_probability, tensor: sequence<map<int64,float32>>
            List<OnnxMap> probabilitySequence = (List<OnnxMap>) results.get(1).getValue();
            OnnxMap onnxMap = probabilitySequence.get(0);
            Map<Long, Float> probMap = (Map<Long, Float>) onnxMap.getValue();
            System.out.println("Probabilidades por classe:");
            for (Map.Entry<Long, Float> entry : probMap.entrySet()) {
                System.out.printf("Classe %d -> %.4f%n", entry.getKey(), entry.getValue());
            }

            return resultado;
        }

    }

}
