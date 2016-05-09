/**
 * 
 */
package pp.block2.cc.ll;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.SymbolFactory;
import pp.block2.cc.Term;

/**
 * Class containing some example grammars.
 * @author Arend Rensink
 *
 */
public class Grammars {
	/** Returns a grammar for simple English sentences. */
    public static Grammar makeSentence() {
        // Define the non-terminals
        NonTerm sent = new NonTerm("Sentence");
        NonTerm subj = new NonTerm("Subject");
        NonTerm obj = new NonTerm("Object");
        NonTerm mod = new NonTerm("Modifier");
        // Define the terminals, using the Sentence.g4 lexer grammar
        SymbolFactory fact = new SymbolFactory(Sentence.class);
        Term noun = fact.getTerminal(Sentence.NOUN);
        Term verb = fact.getTerminal(Sentence.VERB);
        Term adj = fact.getTerminal(Sentence.ADJECTIVE);
        Term end = fact.getTerminal(Sentence.ENDMARK);
        // Build the context free grammar
        Grammar g = new Grammar(sent);
        g.addRule(sent, subj, verb, obj, end);
        g.addRule(subj, noun);
        g.addRule(subj, mod, subj);
        g.addRule(obj, noun);
        g.addRule(obj, mod, obj);
        g.addRule(mod, adj);
        return g;
    }

    public static Grammar makeIf() {
        // Define the non-terminals
        NonTerm stat = new NonTerm("Stat");
        NonTerm elsepart = new NonTerm("ElsePart");
        // Define the terminals, using the Sentence.g4 lexer grammar
        SymbolFactory fact = new SymbolFactory(If.class);
        Term assign = fact.getTerminal(If.ASSIGN);
        Term iff = fact.getTerminal(If.IF);
        Term then = fact.getTerminal(If.THEN);
        Term elsse = fact.getTerminal(If.ELSE);
        Term expr = fact.getTerminal(If.COND);
        // Build the context free grammar
        Grammar g = new Grammar(stat);
        g.addRule(stat, assign);
        g.addRule(stat, iff, expr, then, stat, elsepart);
        g.addRule(elsepart, elsse, stat);
        g.addRule(elsepart, Symbol.EMPTY);
        return g;
    }

    public static Grammar makeL() {
        // Define the non-terminals
        NonTerm l = new NonTerm("L");
        NonTerm r = new NonTerm("R");
        NonTerm q = new NonTerm("Q");
        NonTerm r2 = new NonTerm("R2");
        NonTerm q2 = new NonTerm("Q2");
        // Define the terminals, using the Sentence.g4 lexer grammar
        SymbolFactory fact = new SymbolFactory(L.class);
        Term a = fact.getTerminal(L.A);
        Term b = fact.getTerminal(L.B);
        Term c = fact.getTerminal(L.C);
        // Build the context free grammar
        Grammar g = new Grammar(l);
        g.addRule(l, r, a);
        g.addRule(l, q, b, a);
        g.addRule(r, a, b, a, r2);
        g.addRule(r, c, a, b, a, r2);
        g.addRule(q, b, q2);
        g.addRule(r2, b, c, r2);
        g.addRule(r2, Symbol.EMPTY);
        g.addRule(q2, b, c);
        g.addRule(q2, c);
        return g;
    }
}
