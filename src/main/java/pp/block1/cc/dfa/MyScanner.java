package src.main.java.pp.block1.cc.dfa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wouter on 18-4-2016.
 */
public class MyScanner implements Scanner{
    @Override
    public List<String> scan(State dfa, String text) {
        List<String> res = new ArrayList<>();
        String s = "";
        State begin = dfa;
        for(Character c : text.toCharArray()){
            if (dfa.hasNext(c)) {
                dfa = dfa.getNext(c);
                s += c;
            }
            if (dfa.isAccepting()) {
                res.add(s);
                dfa = begin;
                s = "";
            }
        }
        return res;
    }
}
