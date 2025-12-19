package br.com.oracleone.sentimentapi.main;

import br.com.oracleone.sentimentapi.service.SentimentAnalysisService;

import java.util.Scanner;

public class Main {
    private final Scanner sc = new Scanner(System.in);

    public void showMenu() {
        int opc = -1;
        while (opc != 0) {
            var menu = """
                    --------------------------------
                    1 - Chamar API Sentiment
                    2 - Listar Banco de Analises
                    3 - Listar Banco de Erros
                    
                    0 - Sair
                    --------------------------------\s
                    """;

            System.out.println(menu);
            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    callSentimentAPI();
                    break;
                case 2:
                    listAnalysisDB();
                    break;
                case 3:
                    listErrorsDB();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void callSentimentAPI() {
        try {
            SentimentAnalysisService sas = new SentimentAnalysisService();
            System.out.println("Classificar o Sentimento da Mensagem e Salvar no Banco");
            System.out.println("-------------");
            System.out.print("Digite a Mensagem para Analise: ");
            String text_analyze = sc.nextLine();

            var predict = sas.predict(text_analyze);

            if (predict != null) {
                System.out.println("Predição realizada com sucesso.");
            } else {
                System.out.println("Falha ao obter à analise sentimental da API.");
            }
        }
        catch (Exception e) {
            System.err.println("Uma Exception aconteceu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void listAnalysisDB() {
        System.out.println("Listar todas analises no banco de dados");
        System.out.println("-------------");

        //List<Analysis> analysis = bookService.listBookRegister();
        //analysis.forEach(System.out::println);
    }

    private void listErrorsDB() {
        System.out.println("Listar todos erros no banco de dados");
        System.out.println("-------------");

        //List<Error> errors = bookService.listBookRegister();
        //errors.forEach(System.out::println);
    }
}
