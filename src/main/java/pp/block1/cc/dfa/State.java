package pp.block1.cc.dfa;

import java.util.Map;
import java.util.TreeMap;

/**
 * State of a DFA.
 */
public class State {
	/** State number */
	private final int nr;

	/** Flag indicating if this state is accepting. */
	private final boolean accepting;

	/** Mapping to next states. */
	private final Map<Character, State> next;

	/**
	 * Constructs a new, possibly accepting state with a given number. The
	 * number is meant to identify the state, but there is no check for
	 * uniqueness.
	 */
	public State(int nr, boolean accepting) {
		this.next = new TreeMap<>();
		this.nr = nr;
		this.accepting = accepting;
	}

	/** Returns the state number. */
	public int getNumber() {
		return this.nr;
	}

	/** Indicates if the state is accepting. */
	public boolean isAccepting() {
		return this.accepting;
	}

	/**
	 * Adds an outgoing transition to a next state. This overrides any previous
	 * transition for that character.
	 */
	public void addNext(Character c, State next) {
		this.next.put(c, next);
	}

	/** Indicates if there is a next state for a given character. */
	public boolean hasNext(Character c) {
		return getNext(c) != null;
	}

	/**
	 * Returns the (possibly <code>null</code>) next state for a given
	 * character.
	 */
	public State getNext(Character c) {
		return this.next.get(c);
	}

	@Override
	public String toString() {
		String trans = "";
		for (Map.Entry<Character, State> out : this.next.entrySet()) {
			if (!trans.isEmpty()) {
				trans += ", ";
			}
			trans += "--" + out.getKey() + "-> " + out.getValue().getNumber();
		}
		return String.format("State %d (%s) with outgoing transitions %s",
				this.nr, this.accepting ? "accepting" : "not accepting", trans);
	}

    static final public State ID6_DFA;
    static {
        ID6_DFA = new State(0, false);
        State id61 = new State(1, false);
        State id62 = new State(2, false);
        State id63 = new State(3, false);
        State id64 = new State(4, false);
        State id65 = new State(5, false);
        State id66 = new State(6, true);
        State[] states = { ID6_DFA, id61, id62, id63, id64, id65, id66 };
        for (char c = 'a'; c < 'z'; c++) {
            for (int s = 0; s < states.length - 1; s++) {
                states[s].addNext(c, states[s + 1]);
            }
        }
        for (char c = 'A'; c < 'Z'; c++) {
            for (int s = 0; s < states.length - 1; s++) {
                states[s].addNext(c, states[s + 1]);
            }
        }
        for (char c = '0'; c < '9'; c++) {
            for (int s = 1; s < states.length - 1; s++) {
                states[s].addNext(c, states[s + 1]);
            }
        }
    }

    static final public State LA_DFA;
    static {
        LA_DFA = new State(0, false);
        State q1 = new State(1, false);
        State q2 = new State(2, true);
        State q3 = new State(3, true);
        State q4 = new State(4, false);
        State q5 = new State(5, true);
        State q6 = new State(6, true);
        State q7 = new State(7, false);
        State q8 = new State(8, false);
        State q9 = new State(9, false);
        State q10 = new State(10, false);
        State q11 = new State(11, true);
        State q12 = new State(12, true);
        State[] states = { LA_DFA, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12 };

        char c = 'L';
        for (int s = 0; s < states.length - 1; s++) {
            State state = states[s];
            int num = state.getNumber();
            if (num == 0 | num == 3 | num == 6 | num == 9) {
                state.addNext(c, states[s + 1]);
            } else if (num == 2 | num == 5 | num == 8) {
                state.addNext(c, states[s + 2]);
            }
        }

        c = 'a';
        for (int s = 0; s < states.length - 1; s++) {
            State state = states[s];
            int num = state.getNumber();
            if (num == 1 | num == 4 | num == 7) {
                state.addNext(c, states[s + 1]);
            } else if (num == 2 | num == 5 | num == 8) {
                state.addNext(c, states[s]);
            }
        }

        c = ' ';
        for (int s = 0; s < states.length - 1; s++) {
            State state = states[s];
            int num = state.getNumber();
            if (num == 2 | num == 5 | num == 8 | num == 11) {
                state.addNext(c, states[s + 1]);
            } else if (num == 3 | num == 6 | num == 9 | num == 12) {
                state.addNext(c, states[s]);
            }
        }

        c = 'i';
        for (int s = 0; s < states.length - 1; s++) {
            State state = states[s];
            int num = state.getNumber();
            if (num == 10) {
                state.addNext(c, states[s + 1]);
            }
        }
    }
}
