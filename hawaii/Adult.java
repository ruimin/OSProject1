package hawaii;

/**
 * Provides the behavior for a single adult.
 * @author RuiminCai
 */
public class Adult implements Runnable {
	// Is the adult on Oahu?
	private boolean onOahu = true;
	
	// The boat the adult wants to get into
	private Boat boat;
	
	// The information about where everyone is
	private Hawaii hawaii;
	
	// This adult's id
	private int id;
	
	public Adult (Boat boat, Hawaii hawaii, int id) {
		this.boat = boat;
		this.hawaii = hawaii;
		this.id = id;
	}
	
	public void run() {
		try {
			// Adult is done once it reaches Molokai
			while (onOahu) {
				
				// Lock the boat
				synchronized(boat) {
					// Wait for the boat to be on Molokai
					if (boat.isOnMolokai()) {
						boat.wait();
					}
					
					// Get in the boat if it is empty and there is at least one child on Molokai who
					// will be able to row back or if there are no children on Oahu.  Note that this
					// last situation can only occur if there are no children in the original problem.
					if (boat.isOnOahu() && boat.isEmpty() 
							&& (hawaii.numChildrenOnMolokai() >= 1 || hawaii.numChildrenOnOahu() == 0)) {
						// Get in the boat
						boat.addAdult();
						System.out.println("Adult " + id + " is rowing to Molokai");
						onOahu = false;
						
						// Go to Molokai
						hawaii.addAdultToMolokai();
						boat.gotoMolokai();
						
						// Get out of the boat
						boat.removeAdult();
					}
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
