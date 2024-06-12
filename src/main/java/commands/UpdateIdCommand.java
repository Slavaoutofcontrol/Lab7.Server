package commands;



import collectionClasses.Movie;
import collectionManager.CollectionManager;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import dataBase.DbManager;

import java.io.Serial;

public class UpdateIdCommand implements Command{

    @Serial
    private static final long serialVersionUID = -620855360289409781L;
    private Movie movie;
    private User user;
    public UpdateIdCommand(Movie movie){
        this.movie = movie;
    }
    @Override
    public Response run() {
        DbManager dataBaseManager = new DbManager();
        if (dataBaseManager.updateObject(movie, user.getLogin())) return new Response(ResponseStatus.OK, CollectionManager.updateId(movie));
        return new Response(ResponseStatus.OK, "Это трогать нельзя, элемент принадлежит другому");
    }

    @Override
    public String getCommandName() {
        return "update";
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Movie getMovie() {
        return movie;
    }

    @Override
    public Long getLongArgument() {
        return null;
    }
}
