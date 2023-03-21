package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Author;
import model.Book;
import model.Publisher;
import service.BookService;

public class BookController
{
    private Map<Integer, String> publisher = new HashMap<>();
    private Map<Integer, String> authors   = new HashMap<>();
    private List<Publisher>      publisherList;
    private List<Author>         authorList;
    private BookService          service;

    public BookController()
    {
        service = new BookService();
        getPublisher();
        getAuthors();
    }

    public List<Book> getBooks()
    {
        List<Book> books = new ArrayList<>();
        books = service.getBooks();
        return books;
    }

    private void getPublisher()
    {
        publisherList = service.getPublisher();
        for (Publisher item : publisherList)
        {
            publisher.put(item.getId(), item.getName());
        }
    }

    private void getAuthors()
    {
        authorList = service.getAuthor();
        for (Author item : authorList)
        {
            authors.put(item.getId(), item.getName());
        }
    }

    public boolean updateBook(Book book)
    {
        return service.updateBook(book);
    }

    public boolean addBook(Book book)
    {
        return service.createBook(book);
    }

    public boolean deleteBook(int id)
    {
        return service.deleteBook(id);

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
        return service.searchBooks(searchString.trim(), authorId);
    }

    public Map<Integer, String> getAuthorMap()
    {
        return authors;
    }
}
