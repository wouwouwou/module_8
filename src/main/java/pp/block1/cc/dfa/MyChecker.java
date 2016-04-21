package src.main.java.pp.block1.cc.dfa;

/**
 * Created by Wouter on 18-4-2016.
 */
public class MyChecker implements Checker{
    @Override
    public boolean accepts(State start, String word) {
        State s = start;
        boolean errorstate = false;
        for(Character c : word.toCharArray()) {
            if (s.hasNext(c)) {
                s = s.getNext(c);
            } else {
                errorstate = true;
            }
        }
        return s.isAccepting() && !errorstate;
    }
}
