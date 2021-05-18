import shared.Plane;
import shared.PlaneProxy;
import stubs.Repository;

public class Initializer {
    public static void main(String args[]){
        Repository repo = new Repository();

        Plane plane = new Plane(repo);
        PlaneProxy proxy = new PlaneProxy(plane);
    }
    
}
