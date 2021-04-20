package states;

public class EPilot{
	public enum State {
		AT_TRANSFER_GATE,
		READY_FOR_BOARDING,
		WAIT_FOR_BOARDING,
		FLYING_FORWARD,
    	DEBOARDING,
    	FLYING_BACK
	}

	public enum atTransferGate{
		informPlaneReadyForBoarding,
		endLife
	}

	public enum readyForBoarding{
		waitForAllInBoard
	}

	public enum waitingForBoarding{
		flyToDestinationPoint
	}

	public enum flyingForward{
		announceArrival
	}

	public enum deboarding{
		flyToDeparturePoint
	}

	public enum flyingBack{
		parkAtTransferGate
	}
}
