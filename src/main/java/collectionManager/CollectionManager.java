package collectionManager;


import collectionClasses.Movie;
import collectionClasses.MovieGenre;
import collectionClasses.MpaaRating;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;


/**
 * The {@code CollectionManager} class управляет коллекцией
 *

 */
public class CollectionManager implements Serializable {
    /**
     * Главная коллекция фильмов
     */
    private static HashSet<Movie> movies = new HashSet<>();
    /**
     * Дата инициализации коллекции
     */
    private static LocalDate localDate = LocalDate.now();

    /**
     * Название файл, содержащий коллекцию
     */
    private String filename;

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * Конструктор для класса
     */
    public CollectionManager() {
    }

    /**
     * Устанавливает главную коллекцию
     * @param movies movies
     */
    public void setCollection(HashSet<Movie> movies) {
        this.movies = movies;
    }
    public static HashSet<Movie> getCollection() {
        return movies;
    }

    /**
     * Устанавливает файл, содержащий коллекцию
     * @param filename filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Возвращает главную коллекцию
     * @return HashSet</Movie>
     */
    public static HashSet<Movie> getMovies() {
        return movies;
    }

    /**
     * Выводит информацию о коллекции
     */
    public static String info() {
         return  "Тип коллекции: " + movies.getClass().getSimpleName() + "\nДата инициализации: " + localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\nКоличество элементов: " + movies.size();
    }

    /**
     * Полностью выводит коллекцию
     */
    public static String show() {
        lock.readLock().lock();
        StringBuilder result = new StringBuilder();
        if (!movies.isEmpty()) {
            movies.forEach(movie -> result.append(movie.toString()));
            lock.readLock().unlock();
            return result.toString();
        } else {
            lock.readLock().unlock();
           return "Коллекция не содержит элементов";
        }
    }

    /**
     * Добавляет элемент в коллекцию
     * @param movie movie
     */
    public static void add(Movie movie) {
        lock.writeLock().lock();
        movies.add(movie);
        lock.writeLock().unlock();
    }

    /**
     * Обновляет элемент по переданному идентификатору
     * @param newMovie newMovie
     */
    public static String updateId(Movie newMovie) {
        lock.writeLock().lock();
        boolean flag = movies.stream()
                .filter(movie -> movie.getId() == newMovie.getId())
                .findFirst()
                .map(movie -> {
                    movie.setName(newMovie.getName());
                    movie.setCoordinates(newMovie.getCoordinates());
                    movie.setOscarsCount(newMovie.getOscarsCount());
                    movie.setGenre(newMovie.getGenre());
                    movie.setMpaaRating(newMovie.getMpaaRating());
                    movie.setDirector(newMovie.getDirector());
                    return movie;
                })
                .isPresent();
        lock.writeLock().unlock();
        return !flag ? "Элемента с таким id нет в коллекции" : "Элемент успешно обновлен";
    }
    /**
     * Удаляет элемент по идентификатору
     * @param id id
     */
    public static String removeById(long id) {
        lock.writeLock().lock();
        boolean flag = false;
        for (Movie movie : movies){
            if (movie.getId() == id){
                flag = true;
                movies.remove(movie);
                break;
            }
        }
        lock.writeLock().unlock();
        if (!flag){
            return "Элемента с таким id нет в коллекции";
        } else {
            return "Элемент удален из коллекции";
        }
    }

    /**
     * Очищает коллекцию
     */
    public static void clear() {
        lock.writeLock().lock();
        movies.clear();
        lock.writeLock().unlock();
    }



    /**
     * Добавляет элемент, если он наибольший
     * @param newMovie newMovie
     */
    public static String addIfMax(Movie newMovie) {
        lock.writeLock().lock();
        lock.readLock().lock();
        int maxOscarCount = movies.stream()
                .mapToInt(Movie::getOscarsCount)
                .max()
                .getAsInt();
        lock.readLock().unlock();
        lock.writeLock().unlock();
        if (newMovie.getOscarsCount() > maxOscarCount) {
            add(newMovie);
            return "Элемент успешно добавлен в коллекцию";
        } else {
            return "Элемент не добавлен в коллекцию (не наибольший)";
        }
    }

    /**
     * Добавляет элемент, если он наименьший
     * @param newMovie newMovie
     */
    public static String addIfMin(Movie newMovie) {
        lock.writeLock().lock();
        lock.readLock().lock();
        int minOscarCount = movies.stream()
                .mapToInt(Movie::getOscarsCount)
                .max()
                .getAsInt();
        lock.readLock().unlock();
        lock.writeLock().unlock();
        if (newMovie.getOscarsCount() < minOscarCount) {
            add(newMovie);
            return "Элемент успешно добавлен в коллекцию";
        } else {
            return "Элемент не добавлен в коллекцию (не наибольший)";
        }
    }

    /**
     * Удаляет все элементы, больше переданного
     * @param newMovie newMovie
     */
    public static String removeGreater(Movie newMovie) {
        lock.writeLock().lock();
        movies.removeIf(movie -> movie.getOscarsCount() > newMovie.getOscarsCount());
        movies.add(newMovie);
        lock.writeLock().unlock();
        return "Элементы,больше чем заданный, удалены";
    }

    /**
     * Группирует элементы коллекции по режиссеру
     */
    public static String  groupCountingByDirector() {
        lock.readLock().lock();
        Map<String, List<Movie>> groupedByDirector = movies.stream()
                .collect(Collectors.groupingBy(movie -> String.valueOf(movie.getDirector())));

        StringBuilder result = new StringBuilder();
        groupedByDirector.entrySet().stream()
                .forEach(entry -> {
                    result.append("Режиссер: ").append(entry.getKey()).append("\n");
                    entry.getValue().stream().forEach(movie -> {
                        result.append("   - ").append(movie.getName()).append("\n");
                    });
                });
        lock.readLock().unlock();
        return result.toString();
    }

    /**
     * Выводит элементы коллекции, значение поля MpaaRating которых меньше переданного значения
     * @param mpaaRating mpaaRating
     */
    public static  String countLessThanMpaaRating(MpaaRating mpaaRating) {
        lock.readLock().lock();
        String result = "";
        boolean containsMpaarating = movies.stream()
                .anyMatch(movie -> movie.getMpaaRating() == mpaaRating);
        if(containsMpaarating){Map<MpaaRating, Long> mpaaRatingCount = movies.stream()
                .collect(Collectors.groupingBy(Movie::getMpaaRating, Collectors.counting()));

            long count = mpaaRatingCount.get(mpaaRating);

            long  res = mpaaRatingCount.entrySet().stream()
                    .filter(entry -> entry.getKey() != mpaaRating)
                    .filter(entry -> count > entry.getValue())
                    .mapToLong(Map.Entry::getValue)
                    .sum();
            result = String.valueOf(res);
        } else {
            long res = movies.size();
            result = String.valueOf(res);
        }
        lock.readLock().unlock();
        return "Количество элементов, значение рейтинга которых меньше рейтинга " + mpaaRating +  " : " + result;
    }

    /**
     * Выводит элементы коллекции, значение поля Genre которых больше переданного значения
     * @param genre genre
     */
    public static String countGreaterThanGenre(MovieGenre genre) {
        lock.readLock().lock();
        String result = "";
        boolean containsGenre = movies.stream()
                .anyMatch(movie -> movie.getGenre() == genre);
        if(containsGenre){Map<MovieGenre, Long> genreCount = movies.stream()
                .collect(Collectors.groupingBy(Movie::getGenre, Collectors.counting()));

            long count = genreCount.get(genre);

            long  res = genreCount.entrySet().stream()
                    .filter(entry -> entry.getKey() != genre)
                    .filter(entry -> count < entry.getValue())
                    .mapToLong(Map.Entry::getValue)
                    .sum();
            result = String.valueOf(res);
        } else {
            long res = movies.size();
            result = String.valueOf(res);
        }
        lock.readLock().unlock();
        return "Количество элементов, значение жанра которых больше жанра " + genre  +  " : " + result;
    }
    public static boolean isContainsId(long id) {
        HashSet<Long> ids = new HashSet<>();
        movies.forEach(movie -> ids.add(movie.getId()));
        return (ids.contains(id));
    }
}

