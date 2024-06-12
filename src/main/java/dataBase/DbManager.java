package dataBase;

import collectionClasses.Movie;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import utils.PasswordManager;
import utils.ServerLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;



public class DbManager {
    private QueryManager queryManager = new QueryManager();
    public static Connection connect(){
        try{
            Class.forName("org.postgresql.Driver");
            //для локального
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/collection", "postgres", "841801");
            //для гелиоса
            //return DriverManager.getConnection("jdbc:postgresql://pg:5432/studs");
        } catch (ClassNotFoundException|SQLException e) {
            ServerLogger.getLogger().warning("Ошибка при подключении к базе данных");
        }return null;
    }
    public Response registration(User user){
        try {
            Connection connection = connect();
            PreparedStatement findUser = connection.prepareStatement(queryManager.findUser);
            findUser.setString(1, user.getLogin());
            ResultSet resultSet = findUser.executeQuery();
            if (!resultSet.next()){
                PasswordManager passwordManager = new PasswordManager();
                PreparedStatement addUser = connection.prepareStatement(queryManager.addUser);
                addUser.setString(1, user.getLogin());
                addUser.setString(2, user.getPassword());
                addUser.setString(3, passwordManager.hashPassword(user.getPassword()));
                addUser.execute();
                return new Response(ResponseStatus.OK, "Регистрация прошла успешно!");
            } else {
                return new Response(ResponseStatus.ERROR,"Пользователь с таким логином уже существует. Попробуй еще раз: ");
            }
        } catch (NullPointerException|SQLException e){
            return new Response(ResponseStatus.ERROR,"Ошибка подключения к базе данных. Попробуй еще раз: ");
        }
    }
    public Response authorisation(User user){
        try{
            Connection connection = connect();
            PreparedStatement findUser = connection.prepareStatement(queryManager.findUser);
            findUser.setString(1, user.getLogin());
            ResultSet resultSet = findUser.executeQuery();
            if (resultSet.next()){
                if (resultSet.getString("password").equals(user.getPassword())) return new Response(ResponseStatus.OK, "Вход выполнен успешно!");
                return new Response(ResponseStatus.ERROR, "Введен неверный пароль. Попробуй еще раз: ");
            } else {
                return new Response(ResponseStatus.ERROR, "Пользователь с таким логином не найден. Попробуй еще раз: ");
            }
        } catch (SQLException | NullPointerException e){
            return new Response(ResponseStatus.ERROR, "Ошибка подключения к базе данных. Попробуй еще раз: ");
        }
    }
    public boolean updateObject(Movie newMovie, String user){
        try{
            Connection connection = connect();
            PreparedStatement update = connection.prepareStatement(queryManager.updateObject);
            update.setString(1, newMovie.getName());
            update.setInt(2, newMovie.getCoordinates().getX());
            update.setFloat(3, newMovie.getCoordinates().getY());
            update.setInt(4, newMovie.getOscarsCount());
            update.setString(5, newMovie.getGenre().toString());
            update.setString(6, newMovie.getMpaaRating().toString());
            update.setString(7, newMovie.getDirector().getName());
            update.setDouble(8, newMovie.getDirector().getWeight());
            update.setString(9, newMovie.getDirector().getHairColour().toString());
            update.setString(10, newMovie.getDirector().getNationality().toString());
            update.setString(11, user);
            update.setLong(12, newMovie.getId());
            ResultSet resultSet = update.executeQuery();
            return (resultSet.next());
        } catch (SQLException | NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении/выполнении запроса");
        }
        return false;
    }
    public boolean removeObject(long id, String user_login){
        try{
            Connection connection = connect();
            PreparedStatement remove = connection.prepareStatement(queryManager.deleteObject);
            remove.setString(1, user_login);
            remove.setLong(2, id);
            ResultSet resultSet = remove.executeQuery();
            return resultSet.next();
        } catch (SQLException | NullPointerException e) {
            ServerLogger.getLogger().warning("Ошибка при подключении/выполнении запроса");
        }
        return false;
    }
    public long addObject(Movie movie){
        try{
            Connection connection = connect();
            PreparedStatement add = connection.prepareStatement(queryManager.addMovie);
            add.setString(1, movie.getName());
            add.setInt(2, movie.getCoordinates().getX());
            add.setFloat(3, movie.getCoordinates().getY());
            add.setString(4, movie.getCreationDate().toString());
            add.setInt(5, movie.getOscarsCount());
            add.setString(6, movie.getGenre().toString());
            add.setString(7, movie.getMpaaRating().toString());
            add.setString(8, movie.getDirector().getName());
            add.setDouble(9, movie.getDirector().getWeight());
            add.setString(10, movie.getDirector().getHairColour().toString());
            add.setString(11, movie.getDirector().getNationality().toString());
            add.setString(12, movie.getUser_login());
            ResultSet resultSet = add.executeQuery();
            resultSet.next();
            return resultSet.getLong("id");
        } catch (SQLException|NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении/выполнении запроса");
        }
        return -1;
    }
    public long addIfMax(Movie movie){
        try{
            Connection connection = connect();
            PreparedStatement oscarCount = connection.prepareStatement(queryManager.selectOscarCount);
            ResultSet resultSet = oscarCount.executeQuery();
            ArrayList<Integer> counts = new ArrayList<>();
            while (resultSet.next()){
                counts.add(resultSet.getInt("oscarcount"));
            }
            int maxNum = Collections.max(counts);
            return (movie.getOscarsCount() > maxNum) ? addObject(movie) : -2;
        } catch (SQLException | NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении/выполнении запроса");
        }
        return -1;
    }
    public long addIfMin(Movie movie){
        try{
            Connection connection = connect();
            PreparedStatement oscarCount = connection.prepareStatement(queryManager.selectOscarCount);
            ResultSet resultSet = oscarCount.executeQuery();
            ArrayList<Integer> counts = new ArrayList<>();
            while (resultSet.next()){
                counts.add(resultSet.getInt("oscarcount"));
            }
            int minNum = Collections.min(counts);
            return (movie.getOscarsCount() < minNum) ? addObject(movie) : -2;
        } catch (SQLException | NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении/выполнении запроса");
        }
        return -1;
    }
    public HashSet<Long> clear(String user_login){
        HashSet<Long> ids = new HashSet<>();
        try{
            Connection connection = connect();
            PreparedStatement clear = connection.prepareStatement(queryManager.clearCollection);
            clear.setString(1, user_login);
            ResultSet resultSet = clear.executeQuery();
            while (resultSet.next()){
                ids.add(resultSet.getLong("id"));
            }
        } catch (SQLException | NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении/выполнении запроса");
        }
        return ids;
    }
    public HashSet<Long> removeGreater(String user_login, int oscarCount){
        HashSet<Long> ids = new HashSet<>();
        try{
            Connection connection = connect();
            PreparedStatement removeGreater = connection.prepareStatement(queryManager.removeGreater);
            removeGreater.setString(1, user_login);
            removeGreater.setInt(2, oscarCount);
            ResultSet resultSet = removeGreater.executeQuery();
            while (resultSet.next()){
                ids.add(resultSet.getLong("id"));
            }
        } catch (SQLException | NullPointerException e){
            ServerLogger.getLogger().warning("Ошибка при подключении/выполнении запроса");
        }
        return ids;
    }
}