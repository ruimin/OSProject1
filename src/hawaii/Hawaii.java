package hawaii;

/**
 * Tracks the number of people on each island.
 * Provides the main method for the program
 *
 */
public class Hawaii {
	private static final int NUM_CHILDREN = 2;
	private static final int NUM_ADULTS = 10;
	
	private int numOnOahu = NUM_CHILDREN + NUM_ADULTS;
	private int numChildrenOnOahu = NUM_CHILDREN;

	public int numPeopleOnOahu() {
		return numOnOahu;
	}

	public int numChildrenOnOahu() {
		return numChildrenOnOahu;
	}

	public int numChildrenOnMolokai() {
		return NUM_CHILDREN - numChildrenOnOahu;
	}

	public void addChildToMolokai() {
		assert numChildrenOnOahu > 0;
		numChildrenOnOahu--;
		numOnOahu--;
	}

	public void addChildToOahu() {
		assert numChildrenOnOahu < NUM_CHILDREN;
		numChildrenOnOahu++;
		numOnOahu++;
	}
	
	public void addAdultToMolokai() {
		assert numOnOahu > 0;
		numOnOahu--;
	}

	public static void main(String[] args) {
		Boat boat = new Boat();
		Hawaii hawaii = new Hawaii();
		
		for (int i = 0; i < NUM_CHILDREN; i++) {
			new Thread(new Child(boat, hawaii, i)).start();
		}

		for (int i = 0; i < NUM_ADULTS; i++) {
			new Thread(new Adult(boat, hawaii, i)).start();
		}

	}


}
