package collectionClasses;

import collectionSupport.IdGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * the {@code Movie} class отвечает за фильм
 */
public class Movie implements Serializable{
    @Serial
    private static final long serialVersionUID = -5126503966988816296L;
    /**
     * Уникальный положительный идентификатор фильма
     */
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным
    /**
     * Название фильма, не может быть null, не может быть пустой строчкой
     */
    private String name;
    /**
     * Координаты фильма, не может быть null
     */
    private Coordinates coordinates; //Поле не может быть null
    /**
     * Дата создания фильма, создается автоматически
     */
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**
     * Количество оскаров, должно быть больше 0
     */
    private int oscarsCount; //Значение поля должно быть больше 0
    /**
     * Жанр фильма, не может быть null
     */
    private MovieGenre genre; //Поле может быть null
    /**
     * Рейтинг фильма, не может быть null
     */
    private MpaaRating mpaaRating; //Поле может быть null
    /**
     * Режиссер фильма, не может быть null
     */
    private Person director; //Поле может быть null
    private String user_login;

    /**
     * Конструктор класса, создает обьект по переданных значениям, идентификатор и дата создания создаются автоматически
     *
     * @param name
     * @param coordinates
     * @param oscarsCount
     * @param genre
     * @param mpaaRating
     * @param director
     */
    public Movie(String name, Coordinates coordinates,  int oscarsCount, MovieGenre genre, MpaaRating mpaaRating, Person director) {
        this.id = IdGenerator.generateId();
        this.name=name;
        this.coordinates = coordinates;
        this.oscarsCount  = oscarsCount;
        this.genre=genre;
        this.mpaaRating = mpaaRating;
        this.director = director;
    }
    public Movie(long id, String name, Coordinates coordinates, LocalDate creationDate, int oscarsCount, MovieGenre genre, MpaaRating mpaaRating, Person director, String user_login) {
        this.id = id;
        this.name=name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount  = oscarsCount;
        this.genre=genre;
        this.mpaaRating = mpaaRating;
        this.director = director;
        this.user_login = user_login;
    }
    @Override
    public String toString() {
        return "movie{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"creationDate\": \"" + creationDate.format(DateTimeFormatter.ISO_DATE) + "\", " +
                "\"coordinates\": \"" + coordinates.toString() + "\", " +
                "\"oscarCount\": " + oscarsCount + "\"," +
                "\"genre\": " + (genre == null ? "null" : "\""+genre+"\"") + "\", " +
                "\"mpaaRating\": " + (mpaaRating == null ? "null" : "\""+mpaaRating+"\"") +
                "\"director\": " + director.toString() +"}" + "\", "+
                "\"owner\": " + user_login +"}" + "\"";

    }

    /**
     * Возвращает идентификатор фильма
     * @return
     */
    public long getId() {return id;}

    /**
     * Устанавливает значение идентификатора фильма
     * @param id
     */
    public void setId(long id){this.id = id;}
    /**
     * Возвращает название фильма
     * @return
     */
    public String getName() {return this.name;}
    /**
     * Устанавливает название фильма
     */
    public void setName(String name) {this.name = name;}
    /**
     * Возвращает координаты фильма
     * @return
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    /**
     * Устанавливает координаты фильма
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    /**
     * Возвращает дату создания фильма
     * @return
     */
    public java.time.LocalDate  getCreationDate() {
        return creationDate;
    }
    /**
     * Устанавливает дату создания фильма
     */
    public void setCreationDate() {
        this.creationDate = java.time.LocalDate.now();
    }
    /**
     * Возвращает количество оскаров у фильма
     * @return
     */
    public int getOscarsCount() {
        return oscarsCount;
    }
    /**
     * Устанавливает количество оскаров у фильма
     */
    public void setOscarsCount(int oscarsCount) {
        this.oscarsCount = oscarsCount;
    }
    /**
     * Возвращает жанр фильма
     * @return
     */
    public MovieGenre getGenre() {
        return genre;
    }
    /**
     * Устанавливает жанр фильма
     */
    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }
    /**
     * Возвращает рейтинг фильма
     * @return
     */
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }
    /**
     * Устанавливает рейтинг фильма
     */
    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }
    /**
     * Возвращает режиссера фильма
     * @return
     */
    public Person getDirector() {
        return director;
    }
    /**
     * Устанавливает режиссера фильма
     */
    public void setDirector(Person director) {
        this.director = director;
    }
    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_login() {
        return user_login;
    }
}