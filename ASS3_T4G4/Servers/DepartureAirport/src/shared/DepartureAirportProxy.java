package shared;

import communication.Message;
import communication.MessageType;
import communication.ServerCom;

/**
 * Departure Airport's Proxy
 * @author Bruno Aguiar, 80177
 * @author David Rocha, 84807
 */
public class DepartureAirportProxy {

    /**
     * Instantiation of Departure Airport
     */
    private final DepartureAirport dp;

    /**
     * constructor
     * @param dp Instance of Departure Airport
     */
    public DepartureAirportProxy(DepartureAirport dp) {
        this.dp = dp;
    }
    
     /**
     * @param inMessage Message received
     * @param scon Server connection
     * @return Message to send
     */
    public Message processAndReply(Message inMessage, ServerCom scon){
        Message outMessage = null;
        switch(inMessage.getMessageType()){
            case DP_NO_MORE_PASSENGERS:{
                outMessage = new Message(MessageType.RETURN_NO_PASSENGERS, dp.noMorePassengers(inMessage.getState()));
                break;
            }
            case DP_IS_PLANE_BOARDED:{
                outMessage = new Message(MessageType.RETURN_PLANE_BOARDED, dp.isPlaneBoarded());
                break;
            }
            case PILOT_PARK_TRANSFER_GATE:{
                dp.pilotParkAtTransferGate(inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case PILOT_INFORM_TAKEOFF:{
                dp.pilotInformPlaneReadyForBoarding(inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            } 
            case HOSTESS_PREPARE_FOR_BOARDING:{
                dp.hostessPrepareForPassBoarding(inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case HOSTESS_CHECK_DOCUMENTS:{
                dp.hostessCheckDocuments(inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case HOSTESS_WAIT_FOR_NEXT_PASSENGER:{
                dp.hostessWaitForNextPassenger(inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case HOSTESS_WAIT_FOR_NEXT_FLIGHT:{
                dp.hostessWaitForNextFlight(inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case PASSENGER_TRAVEL_AIRPORT:{
                dp.passengerTravelToAirport(inMessage.getID(), inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case PASSENGER_WAIT_QUEUE:{
                dp.passengerWaitInQueue(inMessage.getID(), inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case PASSENGER_SHOW_DOCUMENTS:{
                dp.passengerShowDocuments(inMessage.getID(), inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case SERVER_SHUTDOWN:{ 
                dp.serverShutdown();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            default:
                break;
        }
        return outMessage;
    }
}