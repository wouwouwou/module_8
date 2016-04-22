package pp.block1.cp.queue;

import pp.block1.cp.queue.exceptions.QueueEmptyException;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by Wouter on 21-4-2016.
 */
public class MyQueue<T> implements Queue<T> {

    private LinkedList<T> queue;

    public MyQueue() {
        this.queue = new LinkedList<>();
    }

    @Override
    public void push(T x) {
        queue.add(x);
    }

    @Override
    public T pull() throws QueueEmptyException {
        T res = null;
        try {
            res = queue.getFirst();
        } catch (NoSuchElementException e) {
            throw new QueueEmptyException();
        }
        queue.remove(res);
        return res;
    }

    @Override
    public int getLength() {
        return queue.size();
    }
}
