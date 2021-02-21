package com.lambdaschool.librarymanager.services;

import com.lambdaschool.librarymanager.models.Book;
import com.lambdaschool.librarymanager.models.User;
import com.lambdaschool.librarymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl
    implements UserService
{
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAll()
    {
        List<User> userList = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(userList::add);
        return userList;
    }

    @Override
    public User findUserById(long userid)
    {
        return userRepository.findById(userid)
            .orElseThrow(() -> new EntityNotFoundException("User id " + userid + " Not Found "));
    }

    @Override
    public User save(User user)
    {
        User newUser = new User();

        if (user.getUserid() != 0){
            userRepository.findById(user.getUserid())
                .orElseThrow(() -> new EntityNotFoundException("User id " + user.getUserid() + "Not Found"));
            newUser.setUserid(user.getUserid());
        }

        newUser.setFname(user.getFname());
        newUser.setLname(user.getLname());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        newUser.getBooks().clear();
        for(Book b : user.getBooks())
        {
            Book newBook = new Book();
            newBook.setTitle(b.getTitle());
            newBook.setAuthor(b.getAuthor());
            newBook.setDescription(b.getDescription());
            newBook.setLevel(b.getLevel());
            newBook.setStatus(b.getStatus());
            newBook.setUser(newUser);
            newUser.getBooks().add(newBook);
        }
        return userRepository.save(newUser);
    }

    @Override
    public User update(long userid, User user)
    {
        User currentUser = userRepository.findById(userid)
            .orElseThrow(() -> new EntityNotFoundException("User " + userid + " Not Found"));

        if (user.getFname() != null){
            currentUser.setFname(user.getFname());
        }
        if (user.getLname() != null){
            currentUser.setLname(user.getLname());
        }
        if (user.getEmail() != null){
            currentUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null){
            currentUser.setPassword(user.getPassword());
        }

        if (user.getBooks().size() > 0)
        {
            currentUser.getBooks()
                .clear();
            for (Book b : user.getBooks())
            {
                Book newBook = new Book();
                newBook.setTitle(b.getTitle());
                newBook.setAuthor(b.getAuthor());
                newBook.setDescription(b.getDescription());
                newBook.setLevel(b.getLevel());
                newBook.setStatus(b.getStatus());
                newBook.setUser(currentUser);
            }
        }
        return userRepository.save(currentUser);
    }

    @Override
    public User signupUser(User user)
    {
        User existingUser = userRepository.findUserByLnameAndFname(user.getLname(), user.getFname());
        if (existingUser != null)
        {
            throw new EntityExistsException("User already exists");
        }else {
           User registeredUser =  save(user);
           return registeredUser;
        }
    }

    @Override
    public void delete(long userid)
    {
        userRepository.findById(userid)
            .orElseThrow(() -> new EntityNotFoundException("User id " +  userid + " Not Found"));
        userRepository.deleteById(userid);
    }

    @Override
    public void deleteAll()
    {
        userRepository.deleteAll();
    }
}
