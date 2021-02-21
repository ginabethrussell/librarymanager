package com.lambdaschool.librarymanager;


import com.github.javafaker.Faker;
import com.lambdaschool.librarymanager.models.Book;
import com.lambdaschool.librarymanager.models.User;
import com.lambdaschool.librarymanager.services.BookService;
import com.lambdaschool.librarymanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Component
public class SeedData
    implements CommandLineRunner
{
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    BookService bookService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;


    @Transactional
    @Override
    public void run(String[] args)
    {
        userService.deleteAll();
        bookService.deleteAll();

        User u1 = new User(
            "cinnamon",
            "bunny",
            "cinnamon@lambdaschool.local",
            "1234567");
        u1.getBooks()
            .add(new Book(
                "Carrot Gardening",
                "I. M. Bunny",
                "A resource for increasing your carrot harvest",
                6,
                1
            ));
        userService.save(u1);

        User u2 = new User(
            "peter",
            "rabbit",
            "peter@lambdaschool.local",
            "1234567");
        u2.getBooks()
            .add(new Book(
                "Bad Bunnies",
                "Rex Rabbit",
                "Strategies for stealing produce",
                4,
                1
            ));
        userService.save(u2);

        User u3 = new User(
            "flopsy",
            "rabbit",
            "flopsy@lambdaschool.local",
            "1234567");
        u3.getBooks()
            .add(new Book(
                "Easy Blackberry Recipes",
                "Mamma Rabbit",
                "Delicious recipes for blackberries",
                3,
                1
            ));
        userService.save(u3);

        Faker dataFaker = new Faker(new Locale("en-US"));
        Random random = new Random();
        Set<String> bookTitles = new HashSet<>();
        for (int i = 0; i < 25; i++)
        {
            bookTitles.add("The Autobiography of " + dataFaker.animal().name());
        }

        for (String title : bookTitles)
        {
            User newUser = new User();
            newUser.setFname(dataFaker.name()
                .firstName());
            newUser.setLname(dataFaker.name()
                .lastName());
            newUser.setEmail(dataFaker.internet()
                .emailAddress());
            newUser.setPassword(dataFaker.princessBride()
                .character());
            Book newBook = new Book();
            newBook.setUser(newUser);
            newBook.setTitle(title);
            newBook.setAuthor(dataFaker.lordOfTheRings()
                .character());
            newBook.setDescription(dataFaker.princessBride()
                .quote());

            newBook.setStatus(random.nextInt(3) +1);
            newBook.setLevel(random.nextInt(6) + 1);
            newUser.getBooks().add(newBook);
            userService.save(newUser);
        }
    }
}



