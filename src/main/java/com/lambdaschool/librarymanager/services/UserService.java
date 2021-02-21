package com.lambdaschool.librarymanager.services;

import com.lambdaschool.librarymanager.models.User;

import java.util.List;

public interface UserService
{
    List<User> findAll();

    User findUserById(long userid);

    User signupUser(User user);

    User save(User user);

    User update(long userid, User user);

    void delete(long userid);

    void deleteAll();
}
