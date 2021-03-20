package monitor;

import states.EPilot;

public class ArrivalAirport {
    public EPilot.deboarding deboarding() {
        return EPilot.deboarding.flyToDeparturePoint;
    }

    public EPilot.flyingBack flyingBack() {
        return EPilot.flyingBack.parkAtTransferGate;
    }
}