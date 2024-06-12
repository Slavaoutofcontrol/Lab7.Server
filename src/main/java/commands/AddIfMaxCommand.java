package commands;

import collectionClasses.Movie;
import collectionManager.CollectionManager;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import dataBase.DbManager;

import java.io.Serial;

public class AddIfMaxCommand implements Command{

    @Serial
    private static final long serialVersionUID = -11081439549547967L;
    private Movie movie;
    private User user;

    public AddIfMaxCommand(Movie movie){
        this.movie = movie;
    }

    @Override
    public Response run() {
        DbManager dataBaseManager = new DbManager();
        long id = dataBaseManager.addIfMax(movie);
        if (id == -1) {
            return new Response(ResponseStatus.OK, "Не удалось выполнить команду");
        } else if (id == -2) {
            return new Response(ResponseStatus.ERROR, "Элемент не добавлен в коллекцию (не наибольший) ");
        } else {
            movie.setId(id);
            return new Response(ResponseStatus.OK, CollectionManager.addIfMax(movie));
        }
    }

    @Override
    public String getCommandName() {
        return "add_if_max";
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
