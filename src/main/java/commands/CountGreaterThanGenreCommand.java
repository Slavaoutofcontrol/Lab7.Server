package commands;

import collectionClasses.Movie;
import collectionClasses.MovieGenre;
import collectionManager.CollectionManager;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;

public class CountGreaterThanGenreCommand implements Command{

    @Serial
    private static final long serialVersionUID = -2442949581870811398L;
    private MovieGenre genre;
    private User user;
    public CountGreaterThanGenreCommand(MovieGenre genre){
        this.genre = genre;
    }
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.countGreaterThanGenre(genre));
    }

    @Override
    public String getCommandName() {
        return "count_greater_than_genre";
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
