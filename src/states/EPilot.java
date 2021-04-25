package states;

/**
 * List of Enumerates for Pilot's state and its transitions
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class EPilot {

	/**
	 * State Enumerate
	 */
	public enum State {
		AT_TRANSFER_GATE,
		READY_FOR_BOARDING,
		WAIT_FOR_BOARDING,
		FLYING_FORWARD,
    	DEBOARDING,
    	FLYING_BACK
	}

	/**
	 * Transition enumerates for {@code EPilot.State.AT_TRANSFER_GATE}
	 */
	public enum atTransferGate {
		informPlaneReadyForBoarding,
		endLife
	}

	/**
	 * Transition enumerates for {@code EPilot.State.READY_FOR_BOARDING}
	 */
	public enum readyForBoarding {
		waitForAllInBoard
	}

	/**
	 * Transition enumerates for {@code EPilot.State.WAIT_FOR_BOARDING}
	 */
	public enum waitingForBoarding {
		flyToDestinationPoint
	}

	/**
	 * Transition enumerates for {@code EPilot.State.FLYING_FORWARD}
	 */
	public enum flyingForward {
		announceArrival
	}

	/**
	 * Transition enumerates for {@code EPilot.State.DEBOARDING}
	 */
	public enum deboarding {
		flyToDeparturePoint
	}

	/**
	 * Transition enumerates for {@code EPilot.State.FLYING_BACK}
	 */
	public enum flyingBack {
		parkAtTransferGate
	}
}
