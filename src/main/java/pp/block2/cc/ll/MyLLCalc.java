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

            //for each rule
            for(Rule p : g.getRules()) {
                //if b is b1b2b3...bk where bi is part of T union NT then
                List<Symbol> b = p.getRHS();
                int k = b.size() - 1;

                //rhs = first(b1) - symbol.empty
                Set<Term> rhs = res.get(b.get(0));
                rhs.remove(Symbol.EMPTY);

                //i = 1
                int i = 0;

                //while symbol.empty is a part of first(bi) and i <= k-1
                while (res.get(b.get(i)).contains(Symbol.EMPTY) && i <= (k-1)) {
                    //rhs = rhs union (first(bi + 1) - symbol.empty)
                    Set<Term> fbi = res.get(b.get(i + 1));
                    fbi.remove(Symbol.EMPTY);
                    rhs.addAll(fbi);

                    //i++
                    i++;
                }

                //if i == k and symbol.empty is a part of first(bk)
                if (i == k && res.get(b.get(k)).contains(Symbol.EMPTY)){
                    //rhs = rhs union symbol.empty
                    rhs.add(Symbol.EMPTY);
                }

                //first(lhs) = first(lhs) union rhs
                res.get(p.getLHS()).addAll(rhs);
            }

            changed = !oldfirst.equals(res);
        }

        return res;
    }

    @Override
    public Map<NonTerm, Set<Term>> getFollow() {
        return null;
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
