package commands;


import collectionClasses.Movie;
import collectionManager.CollectionManager;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import dataBase.DbManager;

import java.io.Serial;

public class RemoveByIdCommand implements Command{

    @Serial
    private static final long serialVersionUID = 904823743523031940L;
    private long id;
    private User user;
    public RemoveByIdCommand(Integer id){
        this.id = id;
    }
    @Override
    public Response run() {
        DbManager dataBaseManager = new DbManager();
        if (dataBaseManager.removeObject(id, user.getLogin())) return new Response(ResponseStatus.OK, CollectionManager.removeById(id));
        return new Response(ResponseStatus.OK, "Невозможно удалить элемент, так как он принадлежит другому пользователю либо элемента с таким id не существует.");
    }

    @Override
    public String getCommandName() {
        return "remove_by_id";
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
        return id;
    }
}
