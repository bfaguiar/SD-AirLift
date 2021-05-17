package states;

/**
 * List of Enumerates for Pilot's state and its transitions
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public enum PilotState {
	AT_TRANSFER_GATE,
	READY_FOR_BOARDING,
	WAIT_FOR_BOARDING,
	FLYING_FORWARD,
	DEBOARDING,
	FLYING_BACK
}
