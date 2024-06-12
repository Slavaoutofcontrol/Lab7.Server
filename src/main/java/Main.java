import collectionClasses.Movie;
import collectionManager.CollectionManager;
import connection.Server;
import dataBase.DbParser;


import java.util.HashSet;


public class Main {
    private final static Integer serverPort = 23468;


    public static void main(String[] args) {
        DbParser dataBaseParser = new DbParser();
        HashSet<Movie> collection = dataBaseParser.readCollection();
        try {
            CollectionManager collectionManager = new CollectionManager();
            collectionManager.setCollection(collection);
            Server server = new Server(serverPort, dataBaseParser);
            server.run();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}