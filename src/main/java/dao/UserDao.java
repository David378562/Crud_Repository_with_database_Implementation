package dao;

import am.basic.db.models.User;

import java.util.List;

public interface UserDao extends CrudDao<User> {
    List<User> findAllByFirstName(String firstName);
}
