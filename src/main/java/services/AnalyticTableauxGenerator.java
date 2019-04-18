package services;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import grammar.Grammar;
import grammar.GrammarBuilder;
import interfaces.Generator;

public class AnalyticTableauxGenerator implements Generator {

    private final int NUM_PROBLEMS = 1000;
    private final int COMPLEXITY_THRESHOLD = 3;
    private final int SET_SIZE_THRESHOLD = 5;
    private static Grammar legitExpressionsGrammar = GrammarBuilder.generateLegitExpressionsGrammar();

    private Random rand = new Random();

    @Override
    public void generateInput() {
        try (PrintWriter inputFile = new PrintWriter("Entrada.in")) {

            inputFile.println(NUM_PROBLEMS);
            for (int i = 0; i < NUM_PROBLEMS; i++) {
                inputFile.println(generateProblem());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Problema ao gerar uma entrada.");
        }
        
    }

    private String generateProblem() {
       int problemType = rand.nextInt(5);

       switch (problemType) {
            case 0:
                return generateSingleExpressionProblem("e satisfativel?");
            case 1:
                return generateSingleExpressionProblem("e refutavel?");
            case 2:
                return generateSingleExpressionProblem("e tautologia?");
            case 3:
                return generateSingleExpressionProblem("e insatisfativel?");
           default:
                return generateLogicalConsequenceProblem();
       }

    }

    private String generateSingleExpressionProblem(String problem) {
        String expression = generateExpression();
        return expression + " " + problem;
    }

    private String generateLogicalConsequenceProblem() {
        String expression = generateExpression();
        String expressionsSet = generateSet();
        return expression + " e consequencia logica de " + expressionsSet + "?";
    }

    private String generateSet() {
        int numExpressions = rand.nextInt(SET_SIZE_THRESHOLD) + 1;
        StringBuilder set = new StringBuilder("{");
        for (int i = 0; i < numExpressions; i++) {
            set.append(generateExpression());
            
            if(i < numExpressions - 1)
                set.append(", ");
        }
        set.append("}");

        return set.toString();
    }

    private String generateExpression() {
        return legitExpressionsGrammar.generateWord(COMPLEXITY_THRESHOLD);
    }
    
}