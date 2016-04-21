package pp.block1.cp.queue;

import pp.block1.cp.queue.exceptions.QueueEmptyException;

import java.util.LinkedList;

/**
 * Created by Wouter on 21-4-2016.
 */
public class QThread implements Runnable {

    private MyQueue<Integer> q;

    public QThread(MyQueue<Integer> q) {
        this.q = q;
    }

    @Override
    public void run() {

    }
}
