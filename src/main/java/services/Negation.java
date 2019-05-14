package services;

public class Negation extends Expression {

    private Expression e;

    public Negation(Expression e) {
        this.e = e;
    }

    @Override
    public boolean evaluate() {
        return !e.evaluate();
    }
    
}