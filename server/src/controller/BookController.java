package controller;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import logger.ServerLogger;
import model.Author;
import model.Book;
import model.Publisher;
import request.BookRequest;
import request.RequestModel;
import response.BookResponse;
import service.BookService;
import utils.Utils;

public class BookController
{
    private Utils        utils = new Utils();
    private OutputStream os    = null;

    public BookController(OutputStream os)
    {
        super();
        this.os = os;
    }

    public void getBooks()
    {
        BookResponse resp = new BookResponse();
        try
        {
            BookService sv = new BookService();
            List<Book> books = sv.getBooksVer2();
            resp.setBooks(books);
            resp.setStatus("Success");

        }
        catch (Exception e)
        {
            resp.setStatus("Fail");
            e.printStackTrace();
            ServerLogger.getInstance().writeLog(e);
        }
        finally
        {
            sendResponse(resp);
        }
    }

    public void deleteBook(RequestModel request)
    {

        BookResponse resp = new BookResponse();
        try
        {
            BookRequest bookReq = (BookRequest) request;
            int id = bookReq.getBook().getId();

            BookService sv = new BookService();
            boolean result = sv.deleteBook(id);
            if (result)
                resp.setStatus("Success");
            else
                resp.setStatus("Fail");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resp.setStatus("Fail");
            ServerLogger.getInstance().writeLog(e);
        }
        finally
        {
            sendResponse(resp);
        }
    }

    /**
     * Update book by id
     * @param request: XML request protocol
     */
    public void updateBook(RequestModel request)
    {

        BookResponse resp = new BookResponse();
        try
        {
            BookRequest bookReq = (BookRequest) request;
            Book dto = bookReq.getBook();
            BookService sv = new BookService();
            boolean result = sv.updateBook(dto);
            if (result)
                resp.setStatus("Success");
            else
                resp.setStatus("Fail");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resp.setStatus("Fail");
            ServerLogger.getInstance().writeLog(e);
        }
        finally
        {
            sendResponse(resp);
        }
    }

    public void createBook(RequestModel request)
    {

        BookResponse resp = new BookResponse();
        try
        {
            BookRequest bookReq = (BookRequest) request;
            Book dto = bookReq.getBook();
            BookService sv = new BookService();
            boolean result = sv.addBook(dto);
            if (result)
                resp.setStatus("Success");
            else
                resp.setStatus("Fail");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resp.setStatus("Fail");
            ServerLogger.getInstance().writeLog(e);
        }
        finally
        {
            sendResponse(resp);
        }
    }

    public void getPublisher()
    {
        BookResponse resp = new BookResponse();
        try
        {
            BookService sv = new BookService();
            List<Publisher> result = sv.getPublisherList();
            resp.setPublishers(result);
            if (result != null)
                resp.setStatus("Success");
            else
                resp.setStatus("Fail");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resp.setStatus("Fail");
            ServerLogger.getInstance().writeLog(e);
        }
        finally
        {
            sendResponse(resp);
        }
    }

    public void getAuthor()
    {
        BookResponse resp = new BookResponse();
        try
        {
            BookService sv = new BookService();
            List<Author> result = sv.getAuthorList();
            resp.setAuthors(result);
            if (result != null)
                resp.setStatus("Success");
            else
                resp.setStatus("Fail");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ServerLogger.getInstance().writeLog(e);
            resp.setStatus("Fail");
        }
        finally
        {
            sendResponse(resp);
        }
    }

    public void searchBooks(RequestModel request)
    {
        BookResponse resp = new BookResponse();
        try
        {
            BookRequest bookReq = (BookRequest) request;
            Book dto = bookReq.getBook();

            BookService sv = new BookService();
            System.out.println(dto.toString() + " ---" + dto.getAuthorId().get(0));
            List<Book> books = sv.searchBooks(dto.getName(), dto.getAuthorId().get(0));
            resp.setBooks(books);
            resp.setStatus("Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resp.setStatus("Fail");
            ServerLogger.getInstance().writeLog(e);
        }
        finally
        {
            sendResponse(resp);
        }
    }

    private void sendResponse(BookResponse resp)
    {
        String responseStr = utils.convertResponseToXml(resp);
        PrintWriter writer = new PrintWriter(os, true);
        writer.println(responseStr);
    }
}
