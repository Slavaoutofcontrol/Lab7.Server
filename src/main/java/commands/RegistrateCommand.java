package commands;

import collectionClasses.Movie;
import connection.Response;
import connection.User;
import dataBase.DbManager;

import java.io.Serial;

public class RegistrateCommand implements Command {
    @Serial
    private static final long serialVersionUID = 4884185358828753678L;
    private User user;

    public RegistrateCommand(User user){
        this. user = user;
    }

    @Override
    public Response run() {
        DbManager dataBaseManager = new DbManager();
        if (user.isExists()) return dataBaseManager.authorisation(user);
        return dataBaseManager.registration(user);
    }

    @Override
    public String getCommandName() {
        return "registrate";
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
