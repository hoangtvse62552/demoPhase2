package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.*;

public class BookController
{
    private Map<Integer, String> publisher = new HashMap<>();
    private Map<Integer, String> authors   = new HashMap<>();
    private List<Publisher>      publisherList;
    private List<Author>         authorList;
    private Map<Integer, Book>   bookMap;

    public BookController()
    {
        getPublisher();
        getAuthors();

    }

    public List<Book> getBooks()
    {
        List<Book> books = new ArrayList<>();

        return books;
    }

    private void getPublisher()
    {

    }

    private void getAuthors()
    {

    }

    private List<Integer> getAuthorOfBook(int bookId)
    {
        List<Integer> authorsId = new ArrayList<>();

        return authorsId;
    }

    public boolean updateBook(Book book)
    {

        return true;
    }

    public boolean addBook(Book book)
    {
        boolean check = false;

        return check;
    }

    public boolean deleteBook(int id)
    {

        return false;

    }

    public List<Publisher> getPublisherList()
    {
        return publisherList;
    }

    public List<Author> getAuthorList()
    {
        return authorList;
    }

    public List<Book> searchBooks(String searchString, int authorId)
    {

        return null;
    }

    public Map<Integer, String> getAuthorMap()
    {
        return authors;
    }

    public List<Book> getBooksVer2()
    {

        return null;
    }
}
