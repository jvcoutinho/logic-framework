package grammar;

public abstract class Symbol {

    private char symbol;
    private boolean isTerminal;

    Symbol(char symbol, boolean isTerminal) {
        this.symbol = symbol;
        this.isTerminal = isTerminal;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol + "";
    }

    public boolean equals(Symbol s) {
        return this.symbol == s.getSymbol();
    }

    public boolean equals(char c) {
        return this.symbol == c;
    }
}