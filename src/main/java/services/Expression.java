package services;

public abstract class Expression {

    private String expression;

    /**
     * @return the expression
     */
    public String getExpression() {
        return expression;
    }

    public abstract boolean evaluate();
    
}