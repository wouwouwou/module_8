package pp.block1.cp.sequence;

/**
 * Created by Wouter on 21-4-2016.
 */
public class UnsafeSequence {

    private int value;

    public int getNext() {
        return value++;
    }

}
