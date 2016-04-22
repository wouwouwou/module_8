package pp.block1.cp.queue;

import pp.block1.cp.queue.exceptions.QueueEmptyException;

import java.util.LinkedList;

/**
 * Created by Wouter on 21-4-2016.
 */
public class QProd implements Runnable {

    private MyQueue<Integer> q;

    public QProd(MyQueue<Integer> q) {
        this.q = q;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 30) {
            System.out.print("Prod " + i + "\n");
            q.push(i);
            i++;
        }
    }
}
