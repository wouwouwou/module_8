package pp.block1.cp.sequence;

/**
 * Created by Wouter on 21-4-2016.
 */
public class USThread implements Runnable {

    private UnsafeSequence u;

    public USThread(UnsafeSequence u) {
        this.u = u;
    }

    @Override
    public void run() {
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
        System.out.print(u.getNext() + " ");
    }
}
