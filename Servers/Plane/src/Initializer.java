import shared.Plane;
import shared.PlaneProxy;
import stubs.Repository;

public class Initializer {
    public static void main(String args[]){
        String server_address = args[0];
        int server_port = Integer.parseInt(args[1]);

        Repository repo = new Repository();
        Plane plane = new Plane(repo);
        PlaneProxy proxy = new PlaneProxy(plane);
    }
    
}
