package pp.block1.cp;

import org.junit.Test;
import pp.block1.cp.queue.MyQueue;
import pp.block1.cp.queue.QThread;
import pp.block1.cp.sequence.USThread;
import pp.block1.cp.sequence.UnsafeSequence;

/**
 * Created by Wouter on 21-4-2016.
 */
public class QueueTest {

    @Test
    public void getNext() throws Exception {
        MyQueue<Integer> q = new MyQueue<>();
        Thread a = new Thread(new QThread(q));
        Thread b = new Thread(new QThread(q));
        Thread c = new Thread(new QThread(q));
        a.start();
        b.start();
        c.start();
        a.join();
        b.join();
        c.join();
    }

}