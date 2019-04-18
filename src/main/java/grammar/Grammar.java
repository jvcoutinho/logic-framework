package grammar;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

// G = (V, E, R, S)
public class Grammar {

    private Set<Variable> V;
    private Set<Terminal> E;
    private Set<Rule> R;
    private Variable S;
    private Random rand = new Random();

    public Grammar(Set<Rule> R, Variable S) {
        this.R = R;
        this.S = S;
        E = new HashSet<>();
        V = new HashSet<>();

        for (Rule r : R) {
            V.add(r.getVariable());
            for (Symbol s : r.getProduction())
                if (s.isTerminal())
                    E.add((Terminal) s);
                else
                    V.add((Variable) s);
        }
    }

    public Variable getS() {
        return S;
    }

    public Set<Rule> getR() {
        return R;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Rule r : this.R) {
            builder.append(r.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    public String generateWord(int threshold) {

        if (threshold == 0)
            return "" + (char) (rand.nextInt('Z' - 'A') + 'A');

        String word = "";

        // Pick a random rule.
        Rule r = (Rule) R.toArray()[rand.nextInt(R.size())];

        for (Symbol s : r.getProduction()) {

            // If it's a non-terminal, recurse.
            if (!s.isTerminal())
                word += generateWord(threshold - 1);
            else if (s.equals('x'))
                word += (char) (rand.nextInt('Z' - 'A') + 'A');
            else if (s.getSymbol() == 'ε')
                word += "";
            else
                word += (Terminal) s;
        }
        return word;
    }

}