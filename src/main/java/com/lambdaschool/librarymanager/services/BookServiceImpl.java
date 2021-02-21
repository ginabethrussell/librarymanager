package com.lambdaschool.librarymanager.services;

import com.lambdaschool.librarymanager.models.Book;
import com.lambdaschool.librarymanager.models.User;
import com.lambdaschool.librarymanager.repository.BookRepository;
import com.lambdaschool.librarymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "bookService")
public class BookServiceImpl implements BookService
{
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Book> findAllBooks()
    {
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(bookList::add);
        return bookList;
    }

    @Override
    public Book findBookById(long id)
    {
        return bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book id " + id + " Not Found"));
    }


    @Override
    public void deleteAll()
    {
        bookRepository.deleteAll();
    }

    @Override
    public List<Book> findBooksByUserId(long userid)
    {
        userRepository.findById(userid)
            .orElseThrow(() -> new EntityNotFoundException("User " + userid + " Not Found"));

        List <Book> userBooks = new ArrayList<>();
        userBooks = bookRepository.findBooksByUser_Userid(userid);
        return userBooks;
    }

    @Override
    public Book addBook(
        long id,
        Book book)
    {
        User currentUser = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User " + id + " Not Found"));

        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        newBook.setDescription(book.getDescription());
        newBook.setLevel(book.getLevel());
        newBook.setStatus(book.getStatus());
        newBook.setUser(currentUser);

        currentUser.getBooks().add(newBook);

        return newBook;
    }

    @Override
    public Book updateBook(
        long userid,
        long bookid,
        Book book)
    {
        User currentUser = userRepository.findById(userid)
            .orElseThrow(() -> new EntityNotFoundException("User " + userid + " Not Found"));

        Book bookToUpdate = bookRepository.findById(bookid)
            .orElseThrow(() -> new EntityNotFoundException("Book " + bookid + " Not Found"));

        if (bookToUpdate.getUser().getUserid() != userid)
        {
            throw new EntityNotFoundException("Book id " + bookid + " Not Found in User id " + userid);
        }

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setDescription(book.getDescription());
        bookToUpdate.setStatus(book.getStatus());
        bookToUpdate.setLevel(book.getLevel());
        bookToUpdate.setUser(currentUser);

        bookRepository.save(bookToUpdate);
        return bookToUpdate;
    }

    @Override
    public void updateBookStatus(
        long userid,
        long bookid,
        String statusid)
    {
        userRepository.findById(userid)
            .orElseThrow(() -> new EntityNotFoundException("User id " + userid + " Not Found"));
        Book updateBook = bookRepository.findById(bookid)
            .orElseThrow(() -> new EntityNotFoundException("Book id " + bookid + " Not Found"));
        updateBook.setStatus(Integer.parseInt(statusid));
        bookRepository.save(updateBook);
    }

    @Override
    public void deleteBookById(
        long userid,
        long bookid)
    {
        User currentUser = userRepository.findById(userid)
            .orElseThrow(() -> new EntityNotFoundException("User id " + userid + " Not Found"));
        Book bookToDelete = bookRepository.findById(bookid)
            .orElseThrow(() -> new EntityNotFoundException("Book id " + bookid + " Not Found"));

        if (bookToDelete.getUser().getUserid() != userid)
        {
            throw new EntityNotFoundException("Book id " + bookid + " Not Found in User id " + userid);
        }
        bookRepository.deleteById(bookid);
    }
}
