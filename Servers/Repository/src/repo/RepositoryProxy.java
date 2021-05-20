package repo;

import communication.Message;
import communication.MessageType;
import communication.ServerCom;

public class RepositoryProxy {
    private final Repository repo;

    public RepositoryProxy(Repository repo) {
        this.repo = repo;
    }
    
    public Message processAndReply(Message inMessage, ServerCom scon){
        Message outMessage = null;
        switch(inMessage.getMessageType()){
            case REPO_SET_HOSTESS_STATE:{
                repo.setHostessState(inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_SET_PILOT_STATE:{
                repo.setPilotState(inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_SET_PASSENGER_STATE:{
                repo.setPassengerListState(inMessage.getID(), inMessage.getState());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_INCREMENT_NUMBER_QUEUE:{
                repo.incrementNumberInQueue();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_DECREMENT_NUMBER_QUEUE:{
                repo.decrementNumberInQueue();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_INCREMENT_NUMBER_PLANE:{
                repo.incrementNumberInPlane();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_DECREMENT_NUMBER_PLANE:{
                repo.incrementNumberInPlane();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_INCREMENT_NUMBER_DESTINATION:{
                repo.incrementNumberAtDestination();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_INCREMENT_FLIGHT_NUM:{
                repo.incrementFlightNum();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_LOG_ENTITIES:{
                repo.logEntities();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_LOG:{
                repo.log();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_LOG_BOARDING:{
                repo.logFlightBoardingStarting();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_LOG_PASSENGER_CHECK:{
                repo.logPassengerCheck(inMessage.getID());
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_LOG_DEPARTURE:{
                repo.logDeparture();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_LOG_ARRIVAL:{
                repo.logArriving();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_LOG_RETURN:{
                repo.logReturning();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            case REPO_CLOSE_LOG:{
                repo.closelog();
                outMessage = new Message(MessageType.STATUS_OK);
                break;
            }
            default:
                break;
        }
        return outMessage;
    }
}
