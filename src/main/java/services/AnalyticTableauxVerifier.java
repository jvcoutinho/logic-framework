package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import interfaces.Verifier;

public class AnalyticTableauxVerifier implements Verifier {

    @Override
    public void verifyOutput() {

        try {
            List<String> correctAnswers = readAnswersFromFile("Saida.out");
            List<String> checkableAnswers = readAnswersFromFile("Saida.out");
            verifyAnswers(correctAnswers, checkableAnswers);
        } catch(IOException e) {
            System.out.println("Erro ao ler arquivos!");
        }
    }

    private List<String> readAnswersFromFile(String path) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(path));
        List<String> lines = reader.lines().collect(Collectors.toList());
        reader.close();
        return lines;
    }

    private void verifyAnswers(List<String> correctAnswers, List<String> checkableAnswers) {
        int errorsNumber = 0;

        for (int i = 0; i < correctAnswers.size(); i++) {
            if(i > checkableAnswers.size())
                System.out.println("Linha não encontrada: " + correctAnswers.get(i));
            else {
                if(!correctAnswers.get(i).equals(checkableAnswers.get(i))) {
                    errorsNumber++;
                    System.out.println("Erro na linha " + i);
                    System.out.println("Encontrado: " + checkableAnswers.get(i));
                    System.out.println("Esperado: " + correctAnswers.get(i));
                }
            }
        }

        System.out.println("Número de erros total: " + errorsNumber);
    }

}