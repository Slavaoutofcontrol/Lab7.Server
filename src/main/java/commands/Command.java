package commands;

import collectionClasses.Movie;
import connection.Response;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

public interface Command extends Serializable{


    Response run();

    String getCommandName();
    User getUser();
    Movie getMovie();
    Long getLongArgument();

}