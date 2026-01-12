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
import java.util.Set;
import java.util.LinkedHashSet;

@Service
public class SentimentAnalysisService {

    private final OrtEnvironment env;
    private final OrtSession session;

    private static final List<String> NEGATIVE_KEYWORDS = List.of(
            "terrível", "terrivel", "horrível", "horrivel", "pior", "lento", "ruim",
            "detestável", "detestavel", "fraco", "quebrado", "lixo", "péssimo", "pessimo",
            "ridículo", "ridiculo", "nojento", "inútil", "inutil", "demorado", "atrasado",
            "caro", "defeito", "falha", "erro", "bug", "travando", "trava", "caiu",
            "fora do ar", "indisponível", "indisponivel", "enganoso", "falso", "mentira",
            "golpe", "bosta", "merda", "odiei", "detestei", "triste", "decepcionado",
            "frustrado", "raiva", "chateado", "insuficiente", "confuso", "bizarro",
            "amador", "descaso", "pena", "problema", "atrasou", "cancelou", "cancelado",
            "cancelamento", "reembolso", "cobrança", "cobranca", "atendimento", "suporte",
            "entrega", "prazo", "produto", "serviço", "servico", "qualidade", "preço",
            "preco", "valor", "site", "aplicativo", "app", "sistema", "vendedor", "loja",
            "worst", "poor", "bad", "broken", "garbage", "useless", "awful", "slow",
            "trash", "disgusting", "horrible", "terrible", "hate", "disappointed", "sad",
            "angry", "failed", "failure", "error", "scam", "fake", "expensive", "late",
            "delay", "down", "service", "support", "delivery", "time", "deadline",
            "product", "quality", "price", "value", "website", "application", "system",
            "seller", "store", "shop", "refund", "cancellation", "billing", "charge",
            "malo", "pésimo", "pesimo", "odio", "basura", "deficiente", "fatal", "asco",
            "agobio", "ansiedad", "angustia", "decepción", "decepcion", "dolor", "enfado",
            "fracaso", "miedo", "pesadilla", "vergüenza", "verguenza", "aburrido",
            "cansado", "aburrimiento", "fallo", "atención", "atencion", "servicio",
            "soporte", "plazo", "tiempo", "producto", "calidad", "precio", "sitio", "web",
            "aplicación", "aplicacion", "tienda", "cancelación", "cancelacion", "cobro");

    private static final List<String> POSITIVE_KEYWORDS = List.of(
            "top", "ótimo", "otimo", "incrível", "incrivel", "maravilhoso", "perfeito",
            "sensacional", "rápido", "rapido", "excelente", "fantástico", "fantastico",
            "lindo", "impecável", "impecavel", "bom", "boa", "amei", "gostei", "adoro",
            "amo", "recomendo", "parabéns", "parabens", "eficiente", "eficaz", "gentil",
            "educado", "atencioso", "barato", "justo", "melhor", "sucesso", "agradável",
            "agradavel", "feliz", "contente", "satisfeito", "nota 10", "10/10", "beleza",
            "atendimento", "suporte", "entrega", "prazo", "produto", "serviço", "servico",
            "qualidade", "preço", "preco", "valor", "site", "aplicativo", "app", "sistema",
            "vendedor", "loja", "rapidez", "agilidade", "amazing", "incredible",
            "fantastic", "fast", "wonderful", "flawless", "excellent", "great", "perfect",
            "good", "very good", "best", "love", "like", "happy", "glad", "pleased",
            "awesome", "cool", "nice", "quick", "service", "support", "delivery", "time",
            "speed", "product", "quality", "price", "value", "website", "application",
            "system", "seller", "store", "shop", "bueno", "genial", "buen", "amor",
            "admirable", "alegría", "alegria", "encanto", "éxito", "exito", "fascinado",
            "gracias", "triunfo", "victoria", "hermoso", "gusto", "satisfacción",
            "satisfaccion", "agradable", "encanta", "gusta", "atención", "atencion",
            "servicio", "soporte", "velocidad", "producto", "calidad", "precio", "sitio",
            "web", "tienda");

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
            var labelResult = results.get("output_label").isPresent() ? results.get("output_label").get()
                    : results.get(0);

            long[] labels = (long[]) results.get(0).getValue();
            long predictedLabel = labels[0];

            String sentiment;
            switch ((int) predictedLabel) {
                case 0:
                    sentiment = "Negativo";
                    break;
                case 1:
                    sentiment = "Neutro";
                    break;
                case 2:
                    sentiment = "Positivo";
                    break;
                default:
                    sentiment = "Desconhecido";
            }

            var probResult = results.get("output_probability").isPresent() ? results.get("output_probability").get()
                    : results.get(1);

            @SuppressWarnings("unchecked")
            List<OnnxMap> probabilitySequence = (List<OnnxMap>) probResult.getValue();
            OnnxMap onnxMap = probabilitySequence.get(0);

            @SuppressWarnings("unchecked")
            Map<Long, Float> probMap = (Map<Long, Float>) onnxMap.getValue();

            float probability = probMap.getOrDefault(predictedLabel, 0.0f);

            List<String> topFeatures = extractTopFeatures(text, sentiment);

            return new PredictionResult(sentiment, (double) probability, topFeatures);
        }
    }

    public List<String> extractTopFeatures(String text, String sentiment) {
        if (text == null || sentiment == null)
            return Collections.emptyList();

        List<String> targetKeywords;
        if ("Positivo".equalsIgnoreCase(sentiment)) {
            targetKeywords = POSITIVE_KEYWORDS;
        } else if ("Negativo".equalsIgnoreCase(sentiment)) {
            targetKeywords = NEGATIVE_KEYWORDS;
        } else {
            // For Neutral, check both to see if any strong words appear (optional, but
            // helpful)
            List<String> all = new ArrayList<>(POSITIVE_KEYWORDS);
            all.addAll(NEGATIVE_KEYWORDS);
            targetKeywords = all;
        }

        // Use a Set to avoid duplicates (e.g. same word in both lists or encountered
        // twice)
        Set<String> found = new LinkedHashSet<>();
        String lowerText = text.toLowerCase();

        for (String word : targetKeywords) {
            // Check if text contains the word. List has specific nouns.
            // Using contains handles unaccented checks if list has them.
            if (lowerText.contains(word.toLowerCase())) {
                found.add(word);
            }
        }
        return new ArrayList<>(found);
    }

    public List<Analysis> processBatch(MultipartFile file, boolean skipHeader) throws Exception {
        List<Analysis> analyses = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                // Pular cabeçalho se a flag estiver ativa
                if (isFirstLine) {
                    isFirstLine = false;
                    if (skipHeader) {
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

    public record PredictionResult(String label, double probability, List<String> topFeatures) {
    }
}