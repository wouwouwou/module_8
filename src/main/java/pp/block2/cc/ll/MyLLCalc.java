package pp.block2.cc.ll;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.Term;

import java.util.*;

/**
 * Created by Wouter on 26-4-2016.
 */
public class MyLLCalc implements LLCalc {

    private Grammar g;

    public MyLLCalc(Grammar g) {
        this.g = g;
    }

    @Override
    public Map<Symbol, Set<Term>> getFirst() {
        Map<Symbol, Set<Term>> res = new HashMap<>();
        Set<Term> s = new HashSet<>();
        s.addAll(g.getTerminals());
        s.add(Symbol.EMPTY);
        s.add(Symbol.EOF);

        for(Term t : s) {
            Set<Term> s1 = new HashSet<>();
            s1.add(t);
            res.put(t, s1);
        }

        for(NonTerm nt : g.getNonterminals()) {
            res.put(nt, new HashSet<>());
        }

        boolean changed = false;
        while(changed) {
            Map<Symbol, Set<Term>> oldfirst = res;

            for(Rule p : g.getRules()) {
                List<Symbol> b = p.getRHS();
                int k = b.size() - 1;
                Set<Term> rhs = res.get(b.get(0));
                rhs.remove(Symbol.EMPTY);
                int i = 0;

                while (res.get(b.get(i)).contains(Symbol.EMPTY) && i <= (k-1)) {
                    Set<Term> fbi = res.get(b.get(i + 1));
                    fbi.remove(Symbol.EMPTY);
                    rhs.addAll(fbi);
                    i++;
                }

                if (i == k && res.get(b.get(k)).contains(Symbol.EMPTY)){
                    rhs.add(Symbol.EMPTY);
                }
                res.get(p.getLHS()).addAll(rhs);
            }
            changed = !oldfirst.equals(res);
        }
        return res;
    }

    @Override
    public Map<NonTerm, Set<Term>> getFollow() {
        HashMap<NonTerm, Set<Term>> res = new HashMap<>();

        for (NonTerm nt : g.getNonterminals()) {
            res.put(nt, new HashSet<>());
        }

        res.get(g.getStart()).add(Symbol.EOF);
        boolean changed = false;
        while(changed) {
            Map<NonTerm, Set<Term>> oldfollow = res;

            for(Rule p : g.getRules()) {
                Set<Term> trailer = res.get(p.getLHS());
                int i = p.getRHS().size() - 1;

                while (i >= 0) {
                    Symbol bi = p.getRHS().get(i);
                    Set<Term> s = getFirst().get(bi);

                    if (bi instanceof NonTerm && g.getNonterminals().contains(bi)) {
                        res.get(bi).addAll(trailer);

                        if (s.contains(Symbol.EMPTY)) {
                            s.remove(Symbol.EMPTY);
                            trailer.addAll(s);
                        } else {
                            trailer = s;
                        }

                    } else trailer = s;
                }
            }
            changed = oldfollow.equals(res);
        }
        return res;
    }

    @Override
    public Map<Rule, Set<Term>> getFirstp() {
        return null;
    }

    @Override
    public boolean isLL1() {
        return false;
    }
}
