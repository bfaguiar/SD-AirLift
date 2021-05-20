package shared;

import communication.Message;
import communication.MessageType;
import communication.ServerCom;

public class ArrivalAirportProxy {
    private final ArrivalAirport ap;

    public ArrivalAirportProxy(ArrivalAirport ap) {
        this.ap = ap;
    }
    
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
            case SERVICE_END:{
                ap.serviceEnd();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            default:
                break;
        }
        return outMessage;
    }
}
