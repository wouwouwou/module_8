package pp.block2.cp.jml;

public class Point {
	/*@ spec_public */private int x;
	/*@ spec_public */private int y = 1;
	//@ public invariant x != y;

	private Object lockX = new Object();
	private Object lockY = new Object();

	//@ ensures \result >= 0; 
	/*@ pure */public int getX() {
		synchronized (lockX) {
			return x;
		}
	}

	//@ ensures \result >= 0; 
	/*@ pure */public int getY() {
		synchronized (lockY) {
			return y;
		}
	}

	/*@ requires n >= 0;
	 *  ensures getX() == \old(getX()) || getX() == \old(getX()) + n; */
	public void moveX(int n) {
		boolean b;
		synchronized (lockX) {
			synchronized (lockY) {
				b = (x + n != y);
			}
		}
		synchronized (lockX) {
			if (b) {
				x = x + n;
			}
		}
	}

	/*@ requires n >= 0;
	 *  ensures getY() == \old(getY()) || getY() == \old(getY() + n); */
	public void moveY(int n) {
		boolean b;
		synchronized (lockY) {
			synchronized (lockX) {
				b = (x != y + n);
			}
		}
		synchronized (lockY) {
			if (b) {
				y = y + n;
			}
		}
	}
}
