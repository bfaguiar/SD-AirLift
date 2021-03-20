package states;

public class EHostess{
	public enum State {
		WAIT_FOR_NEXT_FLIGHT,
		WAIT_FOR_PASSENGER,
		CHECK_PASSENGER,
		READY_TO_FLY
	}

	public enum waitForFlight{
		prepareForPassBoarding
	}

	public enum waitForPassenger{
		checkDocuments,
		informPlaneReadyToTakeOff
	}

	public enum checkPassenger{
		waitForNextPassenger
	}

	public enum readyToFly{
		waitForNextFlight
	}
}