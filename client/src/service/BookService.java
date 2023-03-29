package service;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.Author;
import model.Book;
import model.Publisher;
import request.BookRequest;
import request.RequestModel;
import response.BookResponse;
import response.ResponseModel;
import utils.ConnectManager;
import utils.XmlUtils;

public class BookService
{
    private XmlUtils<RequestModel<BookRequest>> util = new XmlUtils<>();

    public List<Book> getBooks()
    {
        System.out.println("get books");
        List<Book> books = new ArrayList<>();
        RequestModel<BookRequest> req = new RequestModel<>();
        BookRequest book = new BookRequest();
        req.setAction("GetBook");
        req.setData(book);

        String xmlRq = util.convertObjectToXml(req);
        ConnectManager con = new ConnectManager();
        Socket socket = con.sendRequest(xmlRq);
        ResponseModel<BookResponse> resp = con.getResponse(socket);
        if (resp.getStatus().equals("Success"))
        {
            books = resp.getResult().getBooks();
        }

        return books;
    }

    public List<Publisher> getPublisher()
    {
        System.out.println("Get publishers");
        List<Publisher> publishers = new ArrayList<>();
        RequestModel<BookRequest> req = new RequestModel<>();
        req.setAction("GetPublisher");

        String xmlRq = util.convertObjectToXml(req);
        ConnectManager con = new ConnectManager();
        Socket socket = con.sendRequest(xmlRq);
        ResponseModel<BookResponse> resp = con.getResponse(socket);
        if (resp.getStatus().equals("Success"))
        {
            publishers = resp.getResult().getPublishers();
        }
        return publishers;
    }

    public List<Author> getAuthor()
    {
        System.out.println("Get authors");
        List<Author> authors = new ArrayList<>();
        RequestModel<BookRequest> req = new RequestModel<>();
        req.setAction("GetAuthor");

        String xmlRq = util.convertObjectToXml(req);
        ConnectManager con = new ConnectManager();
        Socket socket = con.sendRequest(xmlRq);
        ResponseModel<BookResponse> resp = con.getResponse(socket);
        if (resp.getStatus().equals("Success"))
        {
            authors = resp.getResult().getAuthors();
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
        RequestModel<BookRequest> req = new RequestModel<>();
        BookRequest bookReq = new BookRequest();
        req.setAction("Search");
        bookReq.setBook(dto);
        req.setData(bookReq);

        String xmlRq = util.convertObjectToXml(req);
        ConnectManager con = new ConnectManager();
        Socket socket = con.sendRequest(xmlRq);
        ResponseModel<BookResponse> resp = con.getResponse(socket);
        if (resp.getStatus().equals("Success"))
        {
            System.out.println("search book success");
            if (resp.getResult().getBooks() != null)
                books = resp.getResult().getBooks();
        }
        return books;
    }

    public boolean deleteBook(int id)
    {
        System.out.println("Delete book");
        RequestModel<BookRequest> req = new RequestModel<>();
        BookRequest bookReq = new BookRequest();
        Book dto = new Book();
        dto.setId(id);
        req.setAction("Delete");
        bookReq.setBook(dto);
        req.setData(bookReq);

        String xmlRq = util.convertObjectToXml(req);
        ConnectManager con = new ConnectManager();
        Socket socket = con.sendRequest(xmlRq);
        ResponseModel<BookResponse> resp = con.getResponse(socket);
        if (resp.getStatus().equals("Success"))
        {
            return true;
        }
        return false;
    }

    public boolean createBook(Book book)
    {
        System.out.println("Create book");
        BookRequest bookReq = new BookRequest();
        RequestModel<BookRequest> req = new RequestModel<>();

        req.setAction("Create");
        bookReq.setBook(book);
        req.setData(bookReq);

        String xmlRq = util.convertObjectToXml(req);
        ConnectManager con = new ConnectManager();
        Socket socket = con.sendRequest(xmlRq);
        ResponseModel<BookResponse> resp = con.getResponse(socket);
        if (resp.getStatus().equals("Success"))
        {
            return true;
        }
        return false;
    }

    public boolean updateBook(Book book)
    {
        System.out.println("Update book: ");
        RequestModel<BookRequest> req = new RequestModel<>();
        BookRequest bookReq = new BookRequest();
        req.setAction("Update");
        bookReq.setBook(book);
        req.setData(bookReq);

        String xmlRq = util.convertObjectToXml(req);

        // getting response from server
        ConnectManager con = new ConnectManager();
        Socket socket = con.sendRequest(xmlRq);
        ResponseModel<BookResponse> resp = con.getResponse(socket);
        if (resp.getStatus().equals("Success"))
        {
            return true;
        }
        System.out.println(resp.getError());
        return false;
    }
}
