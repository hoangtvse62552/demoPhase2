package service;

import java.util.ArrayList;
import java.util.List;

import model.Author;
import model.Book;
import model.Publisher;
import request.BookRequest;
import response.BookResponse;
import response.ResponseModel;
import utils.ConnectManager;
import utils.XmlUtils;

public class BookService
{
    public List<Book> getBooks()
    {
        System.out.println("get books");
        List<Book> books = new ArrayList<>();
        BookRequest req = new BookRequest();
        req.setAction("GetBook");

        XmlUtils util = new XmlUtils();
        String xmlRq = util.convertRequestToXml(req);
        ConnectManager con = new ConnectManager();
        BookResponse resp = (BookResponse) con.getResponse(xmlRq);
        if (resp.getStatus().equals("Success"))
        {
            books = resp.getBooks();
        }

        return books;
    }

    public List<Publisher> getPublisher()
    {
        System.out.println("Get publishers");
        List<Publisher> publishers = new ArrayList<>();
        BookRequest req = new BookRequest();
        req.setAction("GetPublisher");

        XmlUtils util = new XmlUtils();
        String xmlRq = util.convertRequestToXml(req);
        ConnectManager con = new ConnectManager();
        BookResponse resp = (BookResponse) con.getResponse(xmlRq);
        if (resp.getStatus().equals("Success"))
        {
            publishers = resp.getPublishers();
        }
        return publishers;
    }

    public List<Author> getAuthor()
    {
        System.out.println("Get authors");
        List<Author> authors = new ArrayList<>();
        BookRequest req = new BookRequest();
        req.setAction("GetAuthor");

        XmlUtils util = new XmlUtils();
        String xmlRq = util.convertRequestToXml(req);
        ConnectManager con = new ConnectManager();
        BookResponse resp = (BookResponse) con.getResponse(xmlRq);
        if (resp.getStatus().equals("Success"))
        {
            authors = resp.getAuthors();
        }
        return authors;
    }

    public List<Book> searchBooks(String searchString, int authorId)
    {
        System.out.println("Search");
        List<Book> books = new ArrayList<>();

        Book dto = new Book();
        dto.setName(searchString);
        List<Integer> authorid = new ArrayList<>();
        authorid.add(authorId);
        dto.setAuthorId(authorid);
        BookRequest req = new BookRequest();
        req.setAction("Search");
        req.setBook(dto);

        XmlUtils util = new XmlUtils();
        String xmlRq = util.convertRequestToXml(req);
        ConnectManager con = new ConnectManager();
        BookResponse resp = (BookResponse) con.getResponse(xmlRq);
        if (resp.getStatus().equals("Success"))
        {
            System.out.println("search book success");
            if (resp.getBooks() != null)
                books = resp.getBooks();
        }
        return books;
    }

    public boolean deleteBook(int id)
    {
        System.out.println("Delete book");
        BookRequest req = new BookRequest();
        Book dto = new Book();
        dto.setId(id);
        req.setAction("Delete");
        req.setBook(dto);
        XmlUtils util = new XmlUtils();
        String xmlRq = util.convertRequestToXml(req);
        ConnectManager con = new ConnectManager();
        BookResponse resp = (BookResponse) con.getResponse(xmlRq);
        if (resp.getStatus().equals("Success"))
        {
            return true;
        }
        return false;
    }

    public boolean createBook(Book book)
    {
        System.out.println("Create book");
        BookRequest req = new BookRequest();

        req.setAction("Create");
        req.setBook(book);
        XmlUtils util = new XmlUtils();
        String xmlRq = util.convertRequestToXml(req);
        ConnectManager con = new ConnectManager();
        BookResponse resp = (BookResponse) con.getResponse(xmlRq);
        if (resp.getStatus().equals("Success"))
        {
            return true;
        }
        return false;
    }
    public boolean updateBook(Book book)
    {
        System.out.println("Update book: ");
        System.out.println("=====================");

        BookRequest req = new BookRequest();
        req.setAction("Update");
        req.setBook(book);

        XmlUtils util = new XmlUtils();
        String xmlRq = util.convertRequestToXml(req);

        // getting response from server
        ConnectManager connectManager = new ConnectManager();
        ResponseModel bookResq = connectManager.getResponse(xmlRq);
        if (bookResq instanceof BookResponse && "Success".equals(bookResq.getStatus()))
        {
            return true;
        }
        System.out.println(bookResq.getError());
        return false;
    }
}
