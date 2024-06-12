package collectionSupport;


import collectionClasses.Movie;

/**
 * The {@code Validator} class проверяет валидность веденных данных
 *
 */
public class Validator {
    private final IdGenerator idGenerator = new IdGenerator();
    /**
     * Конструктор класса
     */
    public Validator(){}

    /**
     * Возвращает фильм, если его поля валидны
     * @param movie movie
     * @return movie
     */
    public Movie getValidatedElement(Movie movie){
        if (movie.getId() < 0 || movie.getName() == null || movie.getName().isEmpty() ||
                movie.getCoordinates() == null || movie.getCoordinates().getX() == null || movie.getCoordinates().getY() > 214||
                movie.getOscarsCount() <= 0 || movie.getDirector().getName() == null||
                movie.getDirector().getName().isEmpty() || movie.getDirector().getWeight() <= 0 ||
                movie.getDirector().getHairColour() == null || movie.getDirector().getNationality() == null){
            return null;
        }else{
            if (movie.getId() == 0){
                movie.setId(IdGenerator.generateId());
            }
            if (movie.getCreationDate() == null){
                movie.setCreationDate();
            }
            return movie;
        }
    }
    public IdGenerator getIdGenerator(){
        return idGenerator;
    }
}