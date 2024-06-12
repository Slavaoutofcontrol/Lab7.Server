package collectionClasses;


import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * the {@code Person} class отвечает за режиссера фильма
 */
public class Person implements Serializable{
    @Serial
    private static final long serialVersionUID = -6596465444728521356L;
    /**
     * Имя режиссера, не может быть null
     */
    private String name; //Поле не может быть null
    /**
     * Вес режиссера, должен быть больше 0
     */
    private double weight; //Значение поля должно быть больше 0
    /**
     * Цвет волос режиссера, не может быть null
     */
    private Colour hairColour; //Поле не может быть null
    /**
     * Национальность режиссера, не может быть null
     */
    private Country nationality; //Поле не может быть null

    /**
     * Конструктор для создания режиссера из переданных значений
     * @param name
     * @param weight
     * @param hairColour
     * @param nationality
     */
    public Person(String name, double weight, Colour hairColour, Country nationality) {
        this.name = name;
        this.weight = weight;
        this.hairColour = hairColour;
        this.nationality = nationality;
    }

    /**
     * Возвращает имя режиссера
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает вес режиссера
     * @return
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Возвращает цвет волос режиссера
     * @return
     */
    public Colour getHairColour() {
        return hairColour;
    }

    /**
     * Возвращает национальность режиссера
     * @return
     */
    public Country getNationality() {
        return nationality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Double.compare(weight, person.weight) == 0 && Objects.equals(name, person.name) && hairColour == person.hairColour && nationality == person.nationality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, hairColour, nationality);
    }

    @Override
    public String toString() {
        return "name: '" + name + '\'' +
                ", weight= " + weight +
                ", hairColour: " + hairColour +
                ", nationality: " + nationality;
    }
}
