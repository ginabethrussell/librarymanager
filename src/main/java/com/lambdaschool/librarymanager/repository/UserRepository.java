package com.lambdaschool.librarymanager.repository;

import com.lambdaschool.librarymanager.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findUserByLnameAndFname(String lname, String fname);
}
