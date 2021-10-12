package repository;


import java.util.List;

/**
 * Base interface for all Data Access Objects
 * */

public interface AbstractDAO<T> {

    T getEntity(T element);

    T getEntityById(Long id);

    List<T> getAll();

    boolean saveEntity(T element);

    boolean deleteEntity(T element);

    boolean updateEntity(T element);


}
