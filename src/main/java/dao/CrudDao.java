package dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    Optional<T> find(int id);
    void save(T t);
    void update(T t);
    void delete(Integer id);

    List<T> findAll();
}
