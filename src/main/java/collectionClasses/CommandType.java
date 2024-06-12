package collectionClasses;


import commands.*;

public enum CommandType {
    ADD(AddCommand.class, "add {element}: добавить новый элемент в коллекцию"),
    ADD_IF_MAX(AddIfMaxCommand.class, "add_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции"),
    ADD_IF_MIN(AddIfMinCommand.class, "add_if_min {element}: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции"),
    CLEAR(ClearCommand.class, "clear: очистить коллекцию"),
    EXECUTE_SCRIPT(ExecuteScriptCommand.class, "execute_script file_name: считать и исполнить скрипт из указанного файла"),
    GROUP_COUNTING_BY_DIRECTOR(GroupCountingByDirectorCommand.class, "group_counting_by_director : сгруппировать элементы коллекции по значению поля director, вывести количество элементов в каждой группе"),
    COUNT_GREATER_THAN_GENRE(CountGreaterThanGenreCommand.class, "count_greater_than_genre genre : вывести количество элементов, значение поля genre которых больше заданного"),
    COUNT_LESS_THAN_MPAA_RATING(CountLessThanMpaaRatingCommand.class, "count_less_than_mpaa_rating mpaaRating : вывести количество элементов, значение поля mpaaRating которых меньше заданного"),
    HELP(HelpCommand.class, "help: вывести справку по доступным командам"),
    INFO(InfoCommand.class, "info: вывести информацию о коллекции"),
    REMOVE_BY_ID(RemoveByIdCommand.class, "remove_by_id id: удалить элемент из коллекции по его id"),
    REMOVE_GREATER(RemoveGreaterCommand.class, "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный"),
    SHOW(ShowCommand.class, "show: вывести все элементы коллекции"),
    UPDATE_ID(UpdateIdCommand.class, "update id {element}: обновить значение элемента коллекции, id которого равен заданному"),
    REGISTRATE(RegistrateCommand.class, ""),
    DEFAULT(DefaultCommand.class, "");
    private final Class<? extends Command> executableClass;
    private final String description;

    CommandType(Class<? extends Command> executableClass, String description) {
        this.executableClass = executableClass;
        this.description = description;
    }

    public Class<? extends Command> getExecutableClass() {
        return executableClass;
    }
    public String getDescription(){
        return description;
    }
}
