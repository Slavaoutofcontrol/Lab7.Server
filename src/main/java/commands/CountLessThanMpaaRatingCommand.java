package commands;

import collectionClasses.Movie;
import collectionClasses.MpaaRating;
import collectionManager.CollectionManager;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;

public class CountLessThanMpaaRatingCommand implements Command{

    @Serial
    private static final long serialVersionUID = -246712803353855092L;
    private MpaaRating mpaaRating;
    private User user;
    public CountLessThanMpaaRatingCommand(MpaaRating mpaaRating){
        this.mpaaRating = mpaaRating;
    }
    @Override
    public Response run() {
        return new Response(ResponseStatus.OK, CollectionManager.countLessThanMpaaRating(mpaaRating));
    }

    @Override
    public String getCommandName() {
        return "count_less_than_mpaarating";
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
