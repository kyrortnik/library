package repository;


import java.util.List;

/**
 * Base interface for all Data Access Objects
 * */

public interface AbstractDAO<T> {

    T get(T t);

    T getById(Long id);

//    List<T> getAll();

    boolean save(T t);

    boolean delete(T t);

    boolean update(T t);


}
