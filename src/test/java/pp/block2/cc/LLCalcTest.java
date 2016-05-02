package pp.block2.cc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.Term;
import pp.block2.cc.ll.*;

public class LLCalcTest {
	/** Tests the LL-calculator for the Sentence grammar. */
	@Test
	public void testSentenceOrig() {
		Grammar g = Grammars.makeSentence();
		// Without the last (recursive) rule, the grammar is LL-1
		assertTrue(createCalc(g).isLL1());
	}

	@Test
	public void testSentenceExtended() {
		Grammar g = Grammars.makeSentence();
		// Without the last (recursive) rule, the grammar is LL-1
		//assertTrue(createCalc(g).isLL1());
		// Now add the last rule, causing the grammar to fail
		// Define the non-terminals
		NonTerm subj = g.getNonterminal("Subject");
		NonTerm obj = g.getNonterminal("Object");
		NonTerm sent = g.getNonterminal("Sentence");
		NonTerm mod = g.getNonterminal("Modifier");
		g.addRule(mod, mod, mod);
		// Define the terminals
		Term adj = g.getTerminal(Sentence.ADJECTIVE);
		Term noun = g.getTerminal(Sentence.NOUN);
		Term verb = g.getTerminal(Sentence.VERB);
		Term end = g.getTerminal(Sentence.ENDMARK);
		LLCalc calc = createCalc(g);
		// FIRST sets
		Map<Symbol, Set<Term>> first = calc.getFirst();
		assertEquals(set(adj, noun), first.get(sent));
		assertEquals(set(adj, noun), first.get(subj));
		assertEquals(set(adj, noun), first.get(obj));
		assertEquals(set(adj), first.get(mod));
		// FOLLOW sets
		Map<NonTerm, Set<Term>> follow = calc.getFollow();
		assertEquals(set(Symbol.EOF), follow.get(sent));
		assertEquals(set(verb), follow.get(subj));
		assertEquals(set(end), follow.get(obj));
		assertEquals(set(noun, adj), follow.get(mod));
		// FIRST+ sets: test per rule
		Map<Rule, Set<Term>> firstp = calc.getFirstp();
		List<Rule> subjRules = g.getRules(subj);
		assertEquals(set(noun), firstp.get(subjRules.get(0)));
		assertEquals(set(adj), firstp.get(subjRules.get(1)));
		// is-LL1-test
		assertFalse(calc.isLL1());
	}

    @Test
    public void testIf() {
        Grammar g = Grammars.makeIf();
        NonTerm stat = g.getNonterminal("Stat");
        NonTerm elsePart = g.getNonterminal("ElsePart");
        Term ifT = g.getTerminal(If.IF);
        Term assign = g.getTerminal(If.ASSIGN);
        Term elsse = g.getTerminal(If.ELSE);
        Term eof = Symbol.EOF;
        Term empty = Symbol.EMPTY;
        LLCalc calc = createCalc(g);
        //FIRST
        Map<Symbol, Set<Term>> first = calc.getFirst();
        assertEquals(set(assign, ifT), first.get(stat));
        assertEquals(set(elsse, empty), first.get(elsePart));
        //FOLLOW
        Map<NonTerm, Set<Term>> follow = calc.getFollow();
        assertEquals(set(eof, elsse), follow.get(stat));
        assertEquals(set(eof, elsse), follow.get(elsePart));
        //FOLLOW
        Map<Rule, Set<Term>> firstp = calc.getFirstp();
        List<Rule> elsePartRules = g.getRules(elsePart);
        assertEquals(set(empty, eof, elsse), firstp.get(elsePartRules.get(1)));
        //LL(1) criteria
        assertFalse(calc.isLL1());
    }

    @Test
    public void testL() {
        Grammar g = Grammars.makeL();
        NonTerm l = g.getNonterminal("L");
        NonTerm r = g.getNonterminal("R");
        NonTerm q = g.getNonterminal("Q");
        NonTerm r2 = g.getNonterminal("R2");
        NonTerm q2 = g.getNonterminal("Q2");
        Term a = g.getTerminal(L.A);
        Term b = g.getTerminal(L.B);
        Term c = g.getTerminal(L.C);
        Term eof = Symbol.EOF;
        Term empty = Symbol.EMPTY;
        LLCalc calc = createCalc(g);
        //FIRST
        Map<Symbol, Set<Term>> first = calc.getFirst();
        assertEquals(set(a, b, c), first.get(l));
        assertEquals(set(a, c), first.get(r));
        assertEquals(set(b), first.get(q));
        assertEquals(set(b, empty), first.get(r2));
        assertEquals(set(b, c), first.get(q2));
        //FOLLOW
        Map<NonTerm, Set<Term>> follow = calc.getFollow();
        assertEquals(set(eof), follow.get(l));
        assertEquals(set(a), follow.get(r));
        assertEquals(set(b), follow.get(q));
        assertEquals(set(a), follow.get(r2));
        assertEquals(set(b), follow.get(q2));
        //FOLLOW
        Map<Rule, Set<Term>> firstp = calc.getFirstp();
        List<Rule> r2rules = g.getRules(r2);
        assertEquals(set(empty, a), firstp.get(r2rules.get(1)));
        //LL(1) criteria
        assertTrue(calc.isLL1());
    }

	/** Creates an LL1-calculator for a given grammar. */
	private LLCalc createCalc(Grammar g) {
		return new MyLLCalc(g); // your implementation of LLCalc (Ex. 2-CC.5)
	}

	@SuppressWarnings("unchecked")
	private <T> Set<T> set(T... elements) {
		return new HashSet<>(Arrays.asList(elements));
	}
}
