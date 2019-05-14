package services;

public class Atomic extends Expression {

    private boolean truthValue;

	@Override
	public boolean evaluate() {
		return truthValue;
	}
    
    
}