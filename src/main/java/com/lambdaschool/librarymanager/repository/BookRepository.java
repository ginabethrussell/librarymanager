package com.lambdaschool.librarymanager.repository;

import com.lambdaschool.librarymanager.models.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long>
{
    List<Book>  findBooksByUser_Userid(long userid);
}
