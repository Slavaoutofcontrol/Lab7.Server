package commands;


import collectionClasses.Movie;
import collectionManager.CollectionManager;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;

public class ShowCommand implements Command{

    @Serial
    private static final long serialVersionUID = 2237136524858086694L;
    private User user;

    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.show());
    }

    @Override
    public String getCommandName() {
        return "show";
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Movie getMovie() {
        return null;
    }

    @Override
    public Long getLongArgument() {
        return null;
    }
}
