package com.lambdaschool.librarymanager.controllers;

import com.lambdaschool.librarymanager.models.Book;
import com.lambdaschool.librarymanager.models.Signup;
import com.lambdaschool.librarymanager.models.User;
import com.lambdaschool.librarymanager.services.BookService;
import com.lambdaschool.librarymanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController
{
    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<?> getAllUsers()
    {
        List<User> userList = userService.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userid}", produces = "application/json")
    public ResponseEntity<?> getUserById(@PathVariable long userid)
    {
        User user = userService.findUserById(userid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/user/signup", consumes = "application/json", produces ="application/json")
    public ResponseEntity<?> addUser(@RequestBody User newUser)
    {
        newUser.setUserid(0);
        newUser = userService.signupUser(newUser);
        Signup signupResponse = new Signup();
        signupResponse.setUserid(newUser.getUserid());
        signupResponse.setToken("wq324refdghyu76453re");
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userid}")
            .buildAndExpand(newUser.getUserid())
            .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(signupResponse, responseHeaders, HttpStatus.CREATED);
    }

    @PostMapping(value = "/user/login", consumes = "application/json", produces ="application/json")
    public ResponseEntity<?> loginUser(@RequestBody User newUser)
    {
        newUser.setUserid(0);
        newUser = userService.save(newUser);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userid}")
            .buildAndExpand(newUser.getUserid())
            .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(newUser, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value="/user/{userid}", consumes="application/json")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User currentUser, @PathVariable long userid)
    {
        currentUser.setUserid(userid);
        currentUser = userService.save(currentUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value="/user/{userid}", consumes="application/json")
    public ResponseEntity<?> editUser(@RequestBody User userData, @PathVariable long userid)
    {
        userData = userService.update(userid, userData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value="/user/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable long userid)
    {
        userService.delete(userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value ="/user/{userid}/books", produces = "application/json")
    public ResponseEntity<?> findUserBooksById(@PathVariable long userid)
    {
        List<Book> userBooks = bookService.findBooksByUserId(userid);
        return new ResponseEntity<>(userBooks, HttpStatus.OK);
    }

    @PostMapping(value ="/user/{userid}/books/book", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addBook(@PathVariable long userid, @Valid @RequestBody Book book)
    {
        book.setBookid(0);
        Book newBook = bookService.addBook(userid, book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping(value ="/user/{userid}/books/book/{bookid}", consumes = "application/json")
    public ResponseEntity<?> updateBook(@PathVariable long userid, @PathVariable long bookid, @Valid @RequestBody Book book)
    {
        Book updatedBook = bookService.updateBook(userid, bookid, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @PatchMapping(value ="/user/{userid}/books/book/{bookid}/status/{statusid}", consumes = "application/json")
    public ResponseEntity<?> updateBookStatus(@PathVariable long userid, @PathVariable long bookid, @PathVariable String statusid)
    {
        bookService.updateBookStatus(userid, bookid, statusid);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @DeleteMapping(value="/user/{userid}/books/{bookid}")
    public ResponseEntity<?> deleteBook(@PathVariable long userid, @PathVariable long bookid)
    {
        bookService.deleteBookById(userid, bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
