package com.lambdaschool.librarymanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookid;

    private String title;

    private String author;

    private String description;

    // ints represent age level
    // 1 = infant - toddler
    // 2 = preK - 2nd
    // 3 = 3rd - 5th
    // 4 = 6th - 8th
    // 5 = 9th - 12th
    // 6 = adult
    private int level;

    // ints represent book status
    // 1 = owned
    // 2 = wishlist
    // 3 = loaned
    // 4 = borrowed
    private  int status;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = "books", allowSetters = true)
    private User user;

    public Book()
    {
    }

    public Book(
        String title,
        String author,
        String description,
        int level,
        int status)
    {
        this.title = title;
        this.author = author;
        this.description = description;
        this.level = level;
        this.status = status;
    }

    public long getBookid()
    {
        return bookid;
    }

    public void setBookid(long bookid)
    {
        this.bookid = bookid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
