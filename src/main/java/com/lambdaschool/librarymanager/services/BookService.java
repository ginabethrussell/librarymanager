package com.lambdaschool.librarymanager.services;

import com.lambdaschool.librarymanager.models.Book;

import java.util.List;

public interface BookService
{
    List<Book> findAllBooks();

    List<Book> findBooksByUserId(long userid);

    Book findBookById(long bookid);

    Book addBook(long userid, Book book);

    Book updateBook(long userid, long bookid, Book book);

    void updateBookStatus(long userid, long bookid, String statusid);

    void deleteBookById(long userid, long bookid);

    void deleteAll();
}
