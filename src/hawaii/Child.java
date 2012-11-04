package hawaii;

/**
 * Provides the behavior of an individual child
 *
 */
public class Child implements Runnable {
	// Is the child on Oahu?
	private boolean onOahu = true;
	
	// The boat the child wants to get into
	private Boat boat;
	
	// The information about where everyone is
	private Hawaii hawaii;
	
	// The child's unique id
	private int id;
	
	public Child (Boat boat, Hawaii hawaii, int id) {
		this.boat = boat;
		this.hawaii = hawaii;
		this.id = id;
	}
	
	public void run() {
		try {
			// Child stops when everyone is on Molokai
			while (hawaii.numPeopleOnOahu() > 0) {
				
				if (onOahu) {
					tryToGoToMolokai();
				}
				
				else {
					tryToGoToOahu();
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void tryToGoToMolokai() throws InterruptedException {
		// Lock the boat
		synchronized(boat) {
			// Wait for the boat to get to Oahu
			while (!boat.isOnOahu()) {
				boat.wait();
			}

			// Get in the boat if it is empty and there is more than one 
			// child on Oahu
			if (boat.isEmpty() && hawaii.numChildrenOnOahu() > 1) {
				boat.addChild();
				
				// Wait for a second child
				boat.wait();
				System.out.println("Child " + id + " is riding to Molokai");
				onOahu = false;
				hawaii.addChildToMolokai();
				boat.removeChild();
				boat.hasPass=true;
			}

			// Get in the boat if there is a child in the boat or this child is the
			// last person on Oahu.
			else if (boat.getNumChildren() == 1 || hawaii.numPeopleOnOahu() == 1){
				boat.addChild();
				
				// Let the first child know the second child got in
				boat.notifyAll();
				System.out.println("Child " + id + " is rowing to Molokai");
				onOahu = false;
				hawaii.addChildToMolokai();

				boat.gotoMolokai();
				boat.removeChild();
				boat.hasPass=false;
			}
			
		}
	}

	private void tryToGoToOahu() throws InterruptedException {
		// Lock the boat
		synchronized(boat) {
			// Wait for the boat to be on Molokai
			//System.out.println("busy waiting");
			while (boat.isOnOahu()||!boat.hasPass) {
				boat.wait();
			}
			
			// If there are people on Oahu, try to take the boat back
			if (hawaii.numPeopleOnOahu() != 0) {
				
				// Get in the boat if it is empty
				if (boat.isEmpty()) { 
					onOahu = true;
					hawaii.addChildToOahu();
					boat.gotoOahu();
					System.out.println("Child " + id + " is rowing to Oahu");
				}
			}
		}
	}

}
