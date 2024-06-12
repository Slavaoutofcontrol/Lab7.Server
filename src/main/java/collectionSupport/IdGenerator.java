package collectionSupport;
import collectionClasses.Movie;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

/**
 * The {@code IdGenerator} class генерирует уникальное значение идентификатора
 *
 */
public class IdGenerator implements Serializable{

    @Serial
    private static final long serialVersionUID = 8844845785630828539L;
    /**
     * HashSet с уже использованными значениями
     */
    private static HashSet<Long> generatedIds = new HashSet<>();

    /**
     * Генерирует идентификатор с помощью {@code System.nanoTime}.
     * @return id
     */
    public static long generateId(){
        long id = System.nanoTime();
        while (generatedIds.contains(id)){
            id = System.nanoTime();
        }
        generatedIds.add(id);
        return id;
    }
    public void addId(Movie movie){
        generatedIds.add(movie.getId());
    }
    public HashSet<Long> getGeneratedIds(){
        return this.generatedIds;
    }
}

