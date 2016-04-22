package pp.block1.cp.queue;

import pp.block1.cp.queue.exceptions.QueueEmptyException;

/**
 * Created by Wouter on 21-4-2016.
 */
public class QCons implements Runnable {

    private MyQueue<Integer> q;

    public QCons(MyQueue<Integer> q) {
        this.q = q;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 10) {
            int j = q.getLength();
            if (j > 0) {
                try {
                    System.out.print(q.pull() + "\n");
                } catch (QueueEmptyException e) {
                    System.out.print("QueueEmptyException!");
                }
                i++;
            } else {

            }
        }
    }
}
