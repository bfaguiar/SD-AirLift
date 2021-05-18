import repo.Repository;
import repo.RepositoryProxy;

public class Initializer {
    public static void main(String args[]){
        Repository repo = new Repository(0);
        RepositoryProxy proxy = new RepositoryProxy(repo);
    }
}
