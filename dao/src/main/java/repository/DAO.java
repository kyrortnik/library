package repository;


import java.util.List;

public interface DAO<T> {

    T get(T element);

    T getById(Long id);

    List<T> getAll();

    boolean save(T element);

    boolean delete(T element);

    boolean update(T element);


}
