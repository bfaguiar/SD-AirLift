package states;

/**
 * List of Enumerates for Passenger's state and its transitions 
 * @author Bruno Aguiar, 80177 * 
 * @author David Rocha, 84807
 */
public class EPassenger {

	/**
	 * State Enumerate
	 */
	public enum State {
		GOING_TO_AIRPORT,
		IN_QUEUE,
		IN_FLIGHT,
		AT_DESTINATION
	}

	/**
	 * Transition enumerates for {@code EPassenger.State.GOING_TO_AIRPORT}
	 */
	public enum goingToAirport {
		travelToAirport,
		waitInQueue
	}

	/**
	 * Transition enumerates for {@code EPassenger.State.IN_QUEUE}
	 */
	public enum inQueue {
		showDocuments,
		boardThePlane
	}

	/**
	 * Transition enumerates for {@code EPassenger.State.IN_FLIGHT}
	 */
	public enum inFlight {
		waitForEndOfFlight,
		leaveThePlane
	}
}