package com.lambdaschool.librarymanager.controllers;

import com.lambdaschool.librarymanager.models.Book;
import com.lambdaschool.librarymanager.services.BookService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController
{
    @Autowired
    BookService bookService;

    @GetMapping(value="/books", produces = "application/json")
    public ResponseEntity<?> getAllBooks()
    {
        List<Book> bookList = new ArrayList<>();
        bookList = bookService.findAllBooks();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping(value="/book/{bookid}", produces = "application/json")
    public ResponseEntity<?> getBookById(@PathVariable long bookid)
    {
        Book book = bookService.findBookById(bookid);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
