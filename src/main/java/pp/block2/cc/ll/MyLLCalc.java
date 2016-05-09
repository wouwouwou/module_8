package pp.block2.cc.ll;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.Term;

import java.util.*;

import static java.util.Collections.disjoint;

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

        boolean changed = true;
        while(changed) {
            Map<Symbol, Set<Term>> oldfirst = new HashMap<>();
            for(Map.Entry<Symbol, Set<Term>> e : res.entrySet()){
                Set<Term> copy = new HashSet<>();
                copy.addAll(e.getValue());
                oldfirst.put(e.getKey(), copy);
            }

            for(Rule p : g.getRules()) {
                Set<Term> rhs = getrhs(p, res);
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
        boolean changed = true;
        while(changed) {
            Map<NonTerm, Set<Term>> oldfollow = new HashMap<>();
            for(Map.Entry<NonTerm, Set<Term>> e : res.entrySet()){
                Set<Term> copy = new HashSet<>();
                copy.addAll(e.getValue());
                oldfollow.put(e.getKey(), copy);
            }

            for(Rule p : g.getRules()) {
                Set<Term> trailer = new HashSet<>();
                trailer.addAll(res.get(p.getLHS()));
                int i = p.getRHS().size() - 1;

                while (i >= 0) {
                    Symbol bi = p.getRHS().get(i);
                    Set<Term> s = new HashSet<>();
                    s.addAll(getFirst().get(bi));

                    if (bi instanceof NonTerm && g.getNonterminals().contains(bi)) {
                        res.get(bi).addAll(trailer);

                        if (s.contains(Symbol.EMPTY)) {
                            s.remove(Symbol.EMPTY);
                            trailer.addAll(s);
                        } else {
                            trailer.clear();
                            trailer.addAll(s);
                        }

                    } else {
                        trailer.clear();
                        trailer.addAll(s);
                    }
                    i--;
                }
            }
            changed = !oldfollow.equals(res);
        }
        return res;
    }

    @Override
    public Map<Rule, Set<Term>> getFirstp() {
        HashMap<Rule, Set<Term>> res = new HashMap<>();
        Map<Symbol, Set<Term>> first = getFirst();
        Map<NonTerm, Set<Term>> follow = getFollow();

        for (Rule p : g.getRules()) {
            Set<Term> firstb = new HashSet<>();
            firstb.addAll(getrhs(p, first));
            if (!firstb.contains(Symbol.EMPTY)) {
                res.put(p, firstb);
            } else {
                firstb.addAll(follow.get(p.getLHS()));
                res.put(p, firstb);
            }
        }
        return res;
    }

    private Set<Term> getrhs(Rule p, Map<Symbol, Set<Term>> res) {
        List<Symbol> b = p.getRHS();
        int k = b.size() - 1;
        Set<Term> rhs = new HashSet<>();
        rhs.addAll(res.get(b.get(0)));
        rhs.remove(Symbol.EMPTY);
        int i = 0;

        while (res.get(b.get(i)).contains(Symbol.EMPTY) && i <= (k-1)) {
            Set<Term> fbi = new HashSet<>();
            fbi.addAll(res.get(b.get(i + 1)));
            fbi.remove(Symbol.EMPTY);
            rhs.addAll(fbi);
            i++;
        }

        if (i == k && res.get(b.get(k)).contains(Symbol.EMPTY)){
            rhs.add(Symbol.EMPTY);
        }
        return rhs;
    }

    @Override
    public boolean isLL1() {
        Map<NonTerm, List<Rule>> rulemap = new HashMap<>();
        for (NonTerm a : g.getNonterminals()) {
            rulemap.put(a, new ArrayList<>());
        }
        for (Rule p : g.getRules()) {
            rulemap.get(p.getLHS()).add(p);
        }
        for (Map.Entry<NonTerm, List<Rule>> e : rulemap.entrySet()) {
            Map<Rule, Set<Term>> firstp = getFirstp();
            List<Rule> rules = e.getValue();
            if (rules.size() > 1) {
                int i = 0;
                while (i < rules.size() - 1) {
                    Set<Term> rule1 = firstp.get(rules.get(i));
                    Set<Term> rule2 = firstp.get(rules.get(i + 1));
                    if (!disjoint(rule1, rule2)) {
                        return false;
                    }
                    i++;
                }
            }
        }
        return true;
    }
}
