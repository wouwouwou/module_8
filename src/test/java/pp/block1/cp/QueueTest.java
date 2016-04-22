package pp.block1.cp;

import org.junit.Test;
import pp.block1.cp.queue.MyQueue;
import pp.block1.cp.queue.QCons;
import pp.block1.cp.queue.QProd;

/**
 * Created by Wouter on 21-4-2016.
 */
public class QueueTest {

    @Test
    public void getNext() throws Exception {
        MyQueue<Integer> q = new MyQueue<>();
        Thread a = new Thread(new QProd(q));
        Thread b = new Thread(new QCons(q));
        Thread c = new Thread(new QCons(q));
        Thread d = new Thread(new QCons(q));
        a.start();
        b.start();
        c.start();
        d.start();
        a.join();
        b.join();
        c.join();
        d.join();
    }

}