package commands;

import collectionClasses.Movie;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;

public class DefaultCommand implements Command{
    @Serial
    private static final long serialVersionUID = -4745382075765516610L;
    private User user;
    @Override
    public Response run() {
        return new Response(ResponseStatus.INFO, "Неизвестная команда. Введите 'help' чтобы получить список доступных команд. ");
    }

    @Override
    public String getCommandName() {
        return null;
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
