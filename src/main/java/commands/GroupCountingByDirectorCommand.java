package commands;


import collectionClasses.Movie;
import connection.Response;
import connection.ResponseStatus;
import collectionManager.CollectionManager;
import connection.User;

import java.io.Serial;

public class GroupCountingByDirectorCommand implements Command{

    @Serial
    private static final long serialVersionUID = -8936229594545431248L;
    private User user;
    public GroupCountingByDirectorCommand(){
    }

    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.groupCountingByDirector());
    }

    @Override
    public String getCommandName() {
        return "group_counting_by_director";
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