package pp.block4.cp;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Wouter on 19-5-2016.
 */
public class CompareAndSet {
    AtomicInteger a = new AtomicInteger();
    CopyOnWriteArrayList c = new CopyOnWriteArrayList();
}
