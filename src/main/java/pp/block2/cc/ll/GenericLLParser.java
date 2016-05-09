package pp.block2.cc.ll;

import java.util.*;

import jdk.nashorn.internal.ir.Terminal;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import pp.block2.cc.*;

/** Generic table-driven LL(1)-parser. */
public class GenericLLParser implements Parser {
	/** The grammar underlying this parser instance. */
	private final Grammar g;
	/** The LL-calculator for the grammar. */
	private final LLCalc calc;
	/** Map from non-terminals to maps of rules indexed by terminal. */
	private Map<NonTerm, Map<Term, Rule>> ll1Table;
	/** Current index in the token list. */
	private int index;
	/** Token list of the currently parsed input. */
	private List<? extends Token> tokens;
    /** Symbolfactory for extracting a term from a token */
    private SymbolFactory fact;


	public GenericLLParser(Grammar g) {
		this.g = g;
		this.calc = new MyLLCalc(g); // here use your own class
	}

	@Override
	public AST parse(Lexer lexer) throws ParseException {
		this.tokens = lexer.getAllTokens();
		this.index = 0;
        this.fact = new SymbolFactory(lexer.getClass());
		return parse(this.g.getStart());
	}

	/** Parses the start of the token stream according to a given
	 * symbol. If it is a terminal, that should be the first token;
	 * otherwise, it is a non-terminal that should be expanded 
	 * according to the next token in the token stream, using the
	 * FIRST+-lookup table and recursively calling {@link #parse(Rule)}
	 * @param symb the symbol according to which the token stream 
	 * should be parsed
	 * @return the sub-AST resulting from the parsing of symb;
	 * or null if the symbol expands to the empty string
	 * @throws ParseException if the symbol cannot be parsed
	 * because the token stream does not contain the expected symbols
	 */
	private AST parse(Symbol symb) throws ParseException {
        AST res = null;
        if (symb instanceof Term) {
            if (index >= tokens.size()) {
                throw new ParseException("Reached eof too soon");
            }
            if(symb != Symbol.EMPTY) {
                try {
                    res = new AST((Term) symb, tokens.get(index));
                } catch (AssertionError e) {
                    throw new ParseException("Token type not equal to symbol type!");
                }
                index++;
            }
        } else {
            Rule r = lookup((NonTerm) symb);
            res = parse(r);
        }
        return res;
	}

	/** Parses the start of the token stream according to a given
	 * rule, recursively calling {@link #parse(Symbol)} to process
	 * the RHS.
	 * @return the sub-AST resulting from the parsing of the rule.
	 * The top node is the node for the LHS of the rule, its direct
	 * children correspond to the symbols of the rule's RHS.
	 * @throws ParseException if the symbol cannot be parsed
	 * because the token stream does not contain the expected symbols
	 */
	private AST parse(Rule r) throws ParseException {
        AST res = new AST(r.getLHS());
        int i = 0;
        while (i < r.getRHS().size()) {
            Symbol symbol = r.getRHS().get(i);
            res.addChild(parse(symbol));
            i++;
        }
        return res;
	}

	/** Uses the lookup table to look up the rule to which
	 * a given nonterminal should be expanded.
	 * The next rule is determined by the next token, using the
	 * FIRST+-set of the nonterminal.
	 * @throws ParseException if the lookup table does not 
	 * contain a rule for the nonterminal in combination with
	 * the first token in the token stream.
	 */
	private Rule lookup(NonTerm nt) throws ParseException {
		Rule result;
		if (atEnd()) {
			result = getLL1Table().get(nt).get(Term.EOF);
			if (result == null) {
				throw new ParseException("Reading beyond end of input");
			}
		} else {
			Token nextToken = peek();
			Term term = this.g.getTerminal(nextToken.getType());
			result = getLL1Table().get(nt).get(term);
			if (result == null) {
				throw new ParseException(String.format(
						"Line %d:%d - no rule for '%s' on token '%s'",
						nextToken.getLine(), nextToken.getCharPositionInLine(),
						nt.getName(), nextToken));
			}
		}
		return result;
	}

	/** Tests whether the end of input has been reached. */
	private boolean atEnd() {
		return this.index >= this.tokens.size();
	}

	/** Returns the next token, without moving the token index. */
	private Token peek() throws ParseException {
		if (this.index >= this.tokens.size()) {
			throw new ParseException("Reading beyond end of input");
		}
		return this.tokens.get(this.index);
	}

	/** Returns the next token and moves up the token index. */
	private Token next() throws ParseException {
		Token result = peek();
		this.index++;
		return result;
	}

	/** Lazily builds and then returns the lookup table. */
	private Map<NonTerm, Map<Term, Rule>> getLL1Table() {
		if (this.ll1Table == null) {
			this.ll1Table = calcLL1Table();
		}
		return this.ll1Table;
	}

	/** Constructs the {@link #ll1Table}. */
	private Map<NonTerm, Map<Term, Rule>> calcLL1Table() {
        Map<NonTerm, Map<Term, Rule>> res = new HashMap<>();
        for (NonTerm A : g.getNonterminals()) {
            Map<Term, Rule> innermap = new HashMap<>();
            for (Term w : g.getTerminals()) {
                innermap.put(w, null);
            }
            res.put(A, innermap);
            for (Rule r : g.getRules()) {
                if (r.getLHS().equals(A)) {
                    Set<Term> firstpset = calc.getFirstp().get(r);
                    for (Term w : firstpset) {
                        res.get(A).put(w, r);
                    }
                    if (firstpset.contains(Symbol.EOF)) {
                        res.get(A).put(Symbol.EOF, r);
                    }
                }
            }
        }
        return res;
	}
}
