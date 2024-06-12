package commands;



import collectionClasses.Movie;
import collectionManager.CollectionManager;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import dataBase.DbManager;

import java.io.Serial;
import java.util.HashSet;

public class ClearCommand implements Command{

    @Serial
    private static final long serialVersionUID = -8925036261411386007L;
    private User user;

    @Override
    public Response run() {
        DbManager dataBaseManager = new DbManager();
        HashSet<Long> ids = dataBaseManager.clear(user.getLogin());
        if (ids.isEmpty()){
            return new Response(ResponseStatus.OK, "Коллекция не содержит элементов, которые вы можете удалить");
        }
        for (long id : ids){
            CollectionManager.removeById(id);
        }
        return new Response(ResponseStatus.OK, "Из коллекции удалены ваши элементы");
    }

    @Override
    public String getCommandName() {
        return "clear";
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
