package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import interfaces.Solver;

public class AnalyticTableauxSolver implements Solver {

    private final String POSITIVE_SATISFIABLE = "Sim, e satisfativel.";
    private final String NEGATIVE_SATISFIABLE = "Nao, nao e satisfativel.";
    private final String POSITIVE_UNSATISFIABLE = "Sim, e insatisfativel.";
    private final String NEGATIVE_UNSATISFIABLE = "Nao, nao e insatisfativel.";
    private final String POSITIVE_REFUTABLE = "Sim, e refutavel.";
    private final String NEGATIVE_REFUTABLE = "Nao, nao e refutavel.";
    private final String POSITIVE_TAUTOLOGY = "Sim, e tautologia.";
    private final String NEGATIVE_TAUTOLOGY = "Nao, nao e tautologia.";
    private final String POSITIVE_LOGICAL_CONSEQUENCE = "Sim, e consequencia logica.";
    private final String NEGATIVE_LOGICAL_CONSEQUENCE = "Nao, nao e consequencia logica.";

    @Override
    public void solveInput() {

        try (PrintWriter outputFile = new PrintWriter("Saida.out")) {
            List<String> problems = readProblemsFromFile();

            for (int i = 1; i < problems.size(); i++) {
                String problem = problems.get(i);
                outputFile.println("Problema #" + i);
                outputFile.println(solveProblem(problem));
                outputFile.println();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Erro ao abrir arquivo.");
        }
    }

    private List<String> readProblemsFromFile() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get("Entrada.in"));
        List<String> lines = reader.lines().collect(Collectors.toList());
        reader.close();
        return lines;
    }

    private String solveProblem(String problem) {
        boolean answer;
        if(problem.contains("e satisfativel")) {
            answer = solveSingleExpressionProblem(problem, true, false);
            return (answer) ? POSITIVE_SATISFIABLE : NEGATIVE_SATISFIABLE;
        } else if(problem.contains("e refutavel")) {
            answer = solveSingleExpressionProblem(problem, false, false);
            return (answer) ? POSITIVE_REFUTABLE : NEGATIVE_REFUTABLE;
        } else if (problem.contains("e tautologia")) {
            answer = solveSingleExpressionProblem(problem, false, true);
            return (answer) ? POSITIVE_TAUTOLOGY : NEGATIVE_TAUTOLOGY;
        } else if (problem.contains("e insatisfativel")) {
            answer = solveSingleExpressionProblem(problem, true, true);
            return (answer) ? POSITIVE_UNSATISFIABLE : NEGATIVE_UNSATISFIABLE;
        } else {
            answer = solveLogicalConsequenceProblem(problem);
            return (answer) ? POSITIVE_LOGICAL_CONSEQUENCE: NEGATIVE_LOGICAL_CONSEQUENCE;
        }
    }

    private boolean solveSingleExpressionProblem(String problem, boolean initialTruthValue, boolean tableauExpectedToClose) {
        String expression = getExpression(problem);
        Node tableau = new Node(expression, initialTruthValue);
        boolean isClosed = runAnalyticTableauxMethod(tableau);
        return isClosed == tableauExpectedToClose;
    }

    private boolean solveLogicalConsequenceProblem(String problem) {
        String expression = getExpression(problem);
        String[] expressionsSet = getExpressionsSet(problem);

        Node tableau = new Node(expressionsSet[0], true);
        for (int i = 1; i < expressionsSet.length; i++) {
            tableau.insertFront(expressionsSet[i], true);
        }
        checkContradictions(tableau.insertFront(expression, false));

        return runAnalyticTableauxMethod(tableau);
    }

    private String getExpression(String problem) {
        int divisorIndex = problem.indexOf(" e ");
        return problem.substring(0, divisorIndex);
    }

    private String[] getExpressionsSet(String problem) {
        int openBracketIndex = problem.indexOf("{");
        return problem.substring(openBracketIndex + 1, problem.length() - 2).split(", ");
    }

    private boolean runAnalyticTableauxMethod(Node tableau) {
        List<Node> appliableNodes;
        while(!tableau.isClosed() && !(appliableNodes = tableau.getAppliableNodes()).isEmpty()) {
            for (Node node : appliableNodes) {
                List<Node> insertedNodes = applyRule(node);
                checkContradictions(insertedNodes);
            }
        }

        return tableau.isClosed();
    }

    private List<Node> applyRule(Node node) {
        node.markApplied();

        int mainOperatorIndex = getMainOperatorIndex(node.getExpression());
        if(mainOperatorIndex != -1) {
            char mainOperator = node.getExpression().charAt(mainOperatorIndex);

            switch (mainOperator) {
                case '~':
                    return applyNegationRule(node, mainOperatorIndex);
                case '>':
                    return applyImplicationRule(node, mainOperatorIndex);
                case 'v':
                    return applyDisjunctionRule(node, mainOperatorIndex);
                default:
                    return applyConjunctionRule(node, mainOperatorIndex);
            }
        }
        return new ArrayList<>();
    }

    private List<Node> applyNegationRule(Node node, int mainOperator) {
        String expression = node.getExpression();
        boolean truthValue = node.getTruthValue();
        String operand = expression.substring(mainOperator + 1, expression.length() - 1);
        
        return node.insertFront(operand, !truthValue);
    }

    private List<Node> applyImplicationRule(Node node, int mainOperator) {
        String expression = node.getExpression();
        boolean truthValue = node.getTruthValue();
        String operand1 = expression.substring(1, mainOperator - 1);
        String operand2 = expression.substring(mainOperator + 2, expression.length() - 1);

        if(truthValue) 
            return node.insertSides(operand1, false, operand2, true);
        else {
            List<Node> insertedNodes = node.insertFront(operand1, true);
            insertedNodes.addAll(node.insertFront(operand2, false));
            return insertedNodes;
        }
    }

    private List<Node> applyConjunctionRule(Node node, int mainOperator) {
        String expression = node.getExpression();
        boolean truthValue = node.getTruthValue();
        String operand1 = expression.substring(1, mainOperator - 1);
        String operand2 = expression.substring(mainOperator + 2, expression.length() - 1);

        if(!truthValue) 
            return node.insertSides(operand1, false, operand2, false);
        else {
            List<Node> insertedNodes = node.insertFront(operand1, true);
            insertedNodes.addAll(node.insertFront(operand2, true));
            return insertedNodes;
        }
    }

    private List<Node> applyDisjunctionRule(Node node, int mainOperator) {
        String expression = node.getExpression();
        boolean truthValue = node.getTruthValue();
        String operand1 = expression.substring(1, mainOperator - 1);
        String operand2 = expression.substring(mainOperator + 2, expression.length() - 1);

        if (truthValue)
            return node.insertSides(operand1, true, operand2, true);
        else {
            List<Node> insertedNodes = node.insertFront(operand1, false);
            insertedNodes.addAll(node.insertFront(operand2, false));
            return insertedNodes;
        }
    }

    private void checkContradictions(List<Node> nodes) {
        for(Node node : nodes) {
            if(node.checkContradiction())
                node.markContradiction();
        }
    }

    private int getMainOperatorIndex(String expression) {
        int numParentesis = 0;
        for(int i = 0; i < expression.length(); i++) {
            if(expression.charAt(i) == '(')
                numParentesis++;
            else if(expression.charAt(i) == ')')
                numParentesis--;
            else if((expression.charAt(i) == 'v' || expression.charAt(i) == '&' || expression.charAt(i) == '~' || expression.charAt(i) == '>') && numParentesis == 1)
                return i;
        }
        return -1;
    }
}