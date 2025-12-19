package br.com.oracleone.sentimentapi;

import br.com.oracleone.sentimentapi.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SentimentapiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SentimentapiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Main main = new Main();
        main.showMenu();
    }
}