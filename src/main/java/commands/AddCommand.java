package commands;


import collectionClasses.Movie;
import collectionManager.CollectionManager;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import dataBase.DbManager;

import java.io.Serial;


public class AddCommand implements Command{

    @Serial
    private static final long serialVersionUID = 4884185358828753717L;
    private Movie movie;
    private User user;

    public AddCommand(Movie movie){
        this.movie = movie;
    }

    @Override
    public Response run() {
        DbManager dataBaseManager = new DbManager();
        long id = dataBaseManager.addObject(movie);
        if (id != -1) {
            movie.setId(id);
            CollectionManager.add(movie);
            return new Response(ResponseStatus.OK, "Элемент успешно добавлен в коллекцию. ");
        } else {
            return new Response(ResponseStatus.OK, "Не получилось добавить элемент в коллекцию.");
        }
    }

    @Override
    public String getCommandName() {
        return "add";
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
