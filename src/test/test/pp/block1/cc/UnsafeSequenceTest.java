package test.pp.block1.cc;

import org.junit.Test;
import pp.block1.cp.sequence.USThread;
import pp.block1.cp.sequence.UnsafeSequence;

/**
 * Created by Wouter on 21-4-2016.
 */
public class UnsafeSequenceTest {

    @Test
    public void getNext() throws Exception {
        UnsafeSequence u = new UnsafeSequence();
        Thread a = new Thread(new USThread(u));
        Thread b = new Thread(new USThread(u));
        a.start();
        b.start();
        a.join();
        b.join();
        System.out.print(u.getNext() + " ");
    }

}