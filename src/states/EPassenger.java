package states;

public class EPassenger{
	public enum State {
		GOING_TO_AIRPORT,
		IN_QUEUE,
		IN_FLIGHT,
		AT_DESTINATION
	}

	public enum goingToAirport{
		travelToAirport,
		waitInQueue
	}

	public enum inQueue{
		showDocuments,
		boardThePlane
	}

	public enum inFlight{
		waitForEndOfFlight,
		leaveThePlane
	}
}