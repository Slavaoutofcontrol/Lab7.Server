package dataBase;

import collectionClasses.*;
import collectionManager.CollectionManager;
import utils.ServerLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;

public class DbParser{
    private QueryManager queryManager = new QueryManager();
    private final Connection connection = DbManager.connect();

    public void writeCollection() {
        try{
            PreparedStatement deleteAll = connection.prepareStatement(queryManager.deleteAll);
            deleteAll.execute();
            HashSet<Movie> movies = CollectionManager.getCollection();
            for (Movie movie : movies){
                PreparedStatement add = connection.prepareStatement(queryManager.addSpacialMovie);
                add.setLong(1, movie.getId());
                add.setString(2, movie.getName());
                add.setInt(3, movie.getCoordinates().getX());
                add.setFloat(4, movie.getCoordinates().getY());
                add.setString(5, movie.getCreationDate().toString());
                add.setInt(6, movie.getOscarsCount());
                add.setString(7, movie.getGenre().toString());
                add.setString(8, movie.getMpaaRating().toString());
                add.setString(9, movie.getDirector().getName());
                add.setDouble(10, movie.getDirector().getWeight());
                add.setString(11, movie.getDirector().getHairColour().toString());
                add.setString(12, movie.getDirector().getNationality().toString());
                add.setString(13, movie.getUser_login());
                add.executeQuery();
            }
            ServerLogger.getLogger().info("Коллекция сохранена в базу данных.");
        } catch (SQLException | NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении к базе данных. Колекция не сохранена.");
        }
    }


    public HashSet<Movie> readCollection() {
        HashSet<Movie> movies = new HashSet<>();
        try{
            PreparedStatement selectAll = connection.prepareStatement(queryManager.selectAllObjects);
            ResultSet result = selectAll.executeQuery();
            while (result.next()){
                long id = result.getLong("id");
                String name = result.getString("name");
                Integer x = result.getInt("coordinate_x");
                float y = result.getFloat("coordinate_y");
                LocalDate creationDate = LocalDate.parse(result.getString("creation_date"));
                int oscarCount = result.getInt("oscarcount");
                MovieGenre genre = MovieGenre.valueOf(result.getString("genre"));
                MpaaRating mpaaRating = MpaaRating.valueOf(result.getString("mpaarating"));
                String director_name = result.getString("director_name");
                double weight = result.getDouble("director_weight");
                Colour director_hairColour = Colour.valueOf(result.getString("director_haircolour"));
                Country director_nationality = Country.valueOf(result.getString("director_nationality"));
                String user_login = result.getString("user_login");
                Movie movie = new Movie(id, name, new Coordinates(x, y), creationDate, oscarCount, genre,
                        mpaaRating, new Person(director_name, weight, director_hairColour, director_nationality), user_login);
                movies.add(movie);
            }
        } catch (SQLException | NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении или чтении данных из базы данных. Создана пустая коллекция");
        }
        return movies;
    }
}