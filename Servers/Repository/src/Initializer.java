import repo.Repository;
import repo.RepositoryProxy;

public class Initializer {
    public static void main(String args[]){
        String server_address = args[0];
        int server_port = Integer.parseInt(args[1]);

        int N_PASSENGERS = Integer.parseInt(args[2]);

        Repository repo = new Repository(N_PASSENGERS);
        RepositoryProxy proxy = new RepositoryProxy(repo);
    }
}
