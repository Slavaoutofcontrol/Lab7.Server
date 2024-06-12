package commands;


import collectionClasses.CommandType;
import collectionClasses.Movie;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HelpCommand implements Command {

    @Serial
    private static final long serialVersionUID = 4831338180725440541L;
    private User user;
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, Arrays.stream(CommandType.values()).
                map(CommandType::getDescription).
                filter(description -> !description.isEmpty()).
                collect(Collectors.joining("\n")));
    }

    @Override
    public String getCommandName() {
        return "help";
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
