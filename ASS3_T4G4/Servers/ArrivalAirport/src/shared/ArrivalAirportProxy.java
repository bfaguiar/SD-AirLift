package shared;

import communication.Message;
import communication.MessageType;
import communication.ServerCom;

/**
 * Arrival Airport's Proxy
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class ArrivalAirportProxy {

    /**
     * Instantiation of Arrival Airport
     */
    private final ArrivalAirport ap;

    /**
     * Proxy's Constructor
     * @param ap instance of Arrival Airport
     */
    public ArrivalAirportProxy(ArrivalAirport ap) {
        this.ap = ap;
    }
    
    /**
     * @param inMessage Message received
     * @param scon Server connection
     * @return Message to send
     */
    public Message processAndReply(Message inMessage, ServerCom scon){
        Message outMessage = null;
        switch(inMessage.getMessageType()){
            case PILOT_FLY_DEPARTURE_POINT:{
                ap.pilotFlyToDeparturePoint(inMessage.getID(), inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case PASSENGER_LEAVE_PLANE:{
                ap.passengerLeaveThePlane(inMessage.getID(), inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case SERVER_SHUTDOWN:{
                ap.serverShutdown();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            default:
                break;
        }
        return outMessage;
    }
}
