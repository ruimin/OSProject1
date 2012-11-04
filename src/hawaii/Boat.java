package hawaii;


/**
 * The boat is the shared resource that the children and adults are contending for.
 *
 */
public class Boat {
	// Number of children on the boat
	private int numChildren = 0;
	
	// Number of adults on the boat
	private int numAdults = 0;
	
	// Is the boat on Oahu?
	private boolean onOahu = true;
	
	boolean hasPass=false;
	
	
	public synchronized int getNumChildren() {
		return numChildren;
	}
	
	public synchronized void addChild() {
		assert (numChildren == 0 || numChildren == 1) && numAdults == 0;
		numChildren++;
	}
	
	public synchronized int getNumAdults() {
		return numAdults;
	}
	
	public synchronized void addAdult() {
		assert numChildren == 0 && numAdults == 0;
		numAdults++;
	}
	
	public synchronized boolean isEmpty() {
		return numChildren == 0 && numAdults == 0;
	}
	
	public synchronized void removeChild() {
		assert numChildren == 1 || numChildren == 2;
		numChildren--;
	}
	
	public synchronized void removeAdult() {
		assert numAdults == 1;
		numAdults = 0;
	}
	
	public synchronized boolean isOnOahu() {
		return onOahu;
	}
	
	public synchronized boolean isOnMolokai() {
		return !onOahu;
	}
	
	/**
	 * Moves the boat to Oahu and wakes up all the waiting threads
	 * in case any of them is waiting for the boat to arrive at
	 * Oahu.
	 */
	public synchronized void gotoOahu() {
		assert !onOahu;
		onOahu = true;
		notifyAll();
	}
	
	/**
	 * Moves the boat to Molokai and wakes up all the waiting threads
	 * in case any of them is waiting for the boat to arrive at
	 * Molokai.
	 */
	public synchronized void gotoMolokai() {
		assert onOahu;
		onOahu = false;
		notifyAll();
	}
}
