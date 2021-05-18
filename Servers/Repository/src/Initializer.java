import repo.Repository;
import repo.RepositoryProxy;

public class Initializer {
    public static void main(String args[]){
        String server_address = args[0];
        int server_port = Integer.parseInt(args[1]);

        Repository repo = new Repository(0);
        RepositoryProxy proxy = new RepositoryProxy(repo);
    }
}
