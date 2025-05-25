import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DFAtoTMConverter {
    static class StateTransition {
        int source;
        char trigger;
        int destination;

        StateTransition(int src, char trg, int dest) {
            this.source = src;
            this.trigger = trg;
            this.destination = dest;
        }

        String getTransition() {
            return "State " + source + " -> State " + destination + " via '" + trigger + "'";
        }
    }

    static class FiniteAutomaton {
        List<StateTransition> transitions = new ArrayList<>();
        Set<Integer> acceptingStates = new HashSet<>();

        void addTransition(int src, char trg, int dest) {
            transitions.add(new StateTransition(src, trg, dest));
        }

        void setAccepting(int state) {
            acceptingStates.add(state);
        }

        boolean validate(String input) {
            int currentState = 0;
            for (char c : input.toCharArray()) {
                boolean found = false;
                for (StateTransition t : transitions) {
                    if (t.source == currentState && t.trigger == c) {
                        currentState = t.destination;
                        found = true;
                        break;
                    }
                }
                if (!found) return false;
            }
            return acceptingStates.contains(currentState);
        }
    }

    static class TuringMachine {
        List<TransitionRule> rules = new ArrayList<>();
        Set<String> finalConfigurations = new HashSet<>();

        void addRule(String src, char read, String dest, char write, char move) {
            rules.add(new TransitionRule(src, read, dest, write, move));
        }

        void setFinal(String state) {
            finalConfigurations.add(state);
        }
    }

    static class TransitionRule {
        String srcState;
        char inputSymbol;
        String destState;
        char outputSymbol;
        char tapeMove;

        TransitionRule(String src, char read, String dest, char write, char move) {
            this.srcState = src;
            this.inputSymbol = read;
            this.destState = dest;
            this.outputSymbol = write;
            this.tapeMove = move;
        }

        String getRule() {
            return srcState + ":" + inputSymbol + "→" + destState + "[" + outputSymbol + "," + tapeMove + "]";
        }
    }

   
    static FiniteAutomaton createAutomaton(String pattern) {
        FiniteAutomaton fa = new FiniteAutomaton();

        if (pattern.equals("a|b")) {
            fa.addTransition(0, 'a', 1);
            fa.addTransition(0, 'b', 1);
            fa.setAccepting(1);
        } else if (pattern.equals("a*b")) {
            fa.addTransition(0, 'a', 0);
            fa.addTransition(0, 'b', 1);
            fa.setAccepting(1);
        } else if (pattern.equals("ab+")) {
            fa.addTransition(0, 'a', 1);
            fa.addTransition(1, 'b', 2);
            fa.addTransition(2, 'b', 2);
            fa.setAccepting(2);
        } else if (pattern.equals("0+")) {
            fa.addTransition(0, '0', 1);
            fa.addTransition(1, '0', 1);
            fa.setAccepting(1);
        } else if (pattern.equals("01*")) {
            fa.addTransition(0, '0', 1);
            fa.addTransition(1, '1', 1);
            fa.setAccepting(1);
        } else {
            throw new IllegalArgumentException("Unsupported pattern");
        }

        return fa;
    }

    static TuringMachine convertToTM(FiniteAutomaton fa) {
        TuringMachine tm = new TuringMachine();

        for (StateTransition t : fa.transitions) {
            String src = "S" + t.source;
            String dest = "S" + t.destination;
            tm.addRule(src, t.trigger, dest, t.trigger, 'R');
        }

        for (int state : fa.acceptingStates) {
            tm.setFinal("S" + state);
        }

        return tm;

    }
}
