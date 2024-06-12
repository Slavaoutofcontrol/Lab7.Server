package dataBase;

public class QueryManager {
    String findUser = "SELECT * FROM users where login = ?;";

    String getPassword = "SELECT hash FROM users where name = ? ;";
    String addUser = "INSERT INTO users(login, password, hash) VALUES (?, ?, ?)";

    String addMovie = """
            insert into movies(name, coordinate_x, coordinate_y, creation_date, oscarcount, genre,
            mpaarating, director_name, director_weight, director_haircolour, director_nationality,
            user_login) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;
            """;

    String addSpacialMovie = """
            INSERT INTO movies(id,name, coordinate_x, coordinate_y, creation_date, oscarcount, genre,
            mpaarating, director_name, director_weight, director_haircolour, director_nationality,
            user_login) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;
            """;

    String clearCollection = "delete from movies where (user_login = ?) returning id;";

    String deleteObject = "delete from movies where (user_login = ?) and (id = ?) returning id;";
    String selectOscarCount = "select oscarCount from movies;";
    String removeGreater = "delete from movies where (user_login = ?) and (oscarcount > ?) returning id;";

    String updateObject = """
            update movies
            set (name, coordinate_x, coordinate_y, oscarcount, genre,
            mpaarating, director_name, director_weight, director_haircolour, director_nationality) = 
            (?,?, ?, ?, ?, ?, ?, ?, ?,?) 
            where (user_login = ?) and (id = ?) returning id;
            """;

    String selectAllObjects = """
            select * from movies;
            """;
    String selectObject = """
            select id, user_login from movies where (user_login = ?) and (id = ?);
            """;
    String deleteAll = "delete from movies;";
}
