package states;

/**
 * List of Enumerates for Hostess' state and its transitions  
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class EHostess {

	/**
	 * State Enumerate
	 */
	public enum State {
		WAIT_FOR_NEXT_FLIGHT,
		WAIT_FOR_PASSENGER,
		CHECK_PASSENGER,
		READY_TO_FLY
	}

	/**
	 * Transition enumerates for {@code EHostess.State.WAIT_FOR_NEXT_FLIGHT}
	 */
	public enum waitForFlight {
		prepareForPassBoarding,
		endLife
	}

	/**
	 * Transition enumerate for {@code EHostess.State.WAIT_FOR_PASSENGER}
	 */
	public enum waitForPassenger {
		checkDocuments,
		informPlaneReadyToTakeOff
	}

	/**
	 * Transition enumerate for {@code EHostess.State.CHECK_PASSENGER}
	 */
	public enum checkPassenger {
		waitForNextPassenger
	}

	/**
	 * Transition enumerate for {@code EHostess.State.READY_TO_FLY}
	 */
	public enum readyToFly {
		waitForNextFlight
	}
} 