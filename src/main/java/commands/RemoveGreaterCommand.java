package commands;

import collectionClasses.Movie;
import collectionManager.CollectionManager;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import dataBase.DbManager;

import java.io.Serial;
import java.util.HashSet;

public class RemoveGreaterCommand implements Command{

    @Serial
    private static final long serialVersionUID = -6679190409592074342L;
    private Movie movie;
    private User user;
    public RemoveGreaterCommand(Movie movie){
        this.movie = movie;
    }
    @Override
    public Response run() {
        DbManager dataBaseManager = new DbManager();
        HashSet<Long> ids = dataBaseManager.removeGreater(user.getLogin(), movie.getOscarsCount());
        if (!ids.isEmpty()){
            for (long id : ids){
                CollectionManager.removeById(id);
            }
            movie.setId(dataBaseManager.addObject(movie));
            CollectionManager.add(movie);
            return new Response(ResponseStatus.OK, "Ваши элементы, большие чем заданный удалены");
        }
        return new Response(ResponseStatus.OK, "В коллекции нет доступных элементов для удаления");
    }

    @Override
    public String getCommandName() {
        return "remove_lower";
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