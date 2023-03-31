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
import response.ResponseModel;
import service.BookService;
import utils.XmlUtils;

public class BookController
{
    private XmlUtils<BookResponse> utilsResponse = new XmlUtils<>();
    private XmlUtils<BookRequest>  utilsReq      = new XmlUtils<>();
    private OutputStream           os            = null;

    public BookController(OutputStream os)
    {
        super();
        this.os = os;
    }

    public void getBooks()
    {
        ResponseModel<BookResponse> resp = new ResponseModel<>();
        try
        {
            BookService sv = new BookService();
            List<Book> books = sv.getBooksVer2();
            BookResponse bookResp = new BookResponse();
            bookResp.setBooks(books);

            resp.setResult(bookResp);
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
            resp.setAction("GetBook");
            sendResponse(resp);
        }
    }

    public void deleteBook(String xmlString)
    {

        ResponseModel<BookResponse> resp = new ResponseModel<>();
        try
        {
            RequestModel<BookRequest> bookReq = (RequestModel<BookRequest>) utilsReq.convertXmlToObject(xmlString);
            int id = bookReq.getData().getBook().getId();

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
            resp.setAction("Delete");
            sendResponse(resp);
        }
    }

    public void updateBook(String xmlString)
    {

        ResponseModel<BookResponse> resp = new ResponseModel<>();
        try
        {
            RequestModel<BookRequest> bookReq = (RequestModel<BookRequest>) utilsReq.convertXmlToObject(xmlString);
            Book dto = bookReq.getData().getBook();
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
            resp.setAction("Update");
            sendResponse(resp);
        }
    }

    public void createBook(String xmlString)
    {

        ResponseModel<BookResponse> resp = new ResponseModel<>();
        try
        {
            RequestModel<BookRequest> bookReq = (RequestModel<BookRequest>) utilsReq.convertXmlToObject(xmlString);
            Book dto = bookReq.getData().getBook();
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
            resp.setAction("Create");
            sendResponse(resp);
        }
    }

    public void getPublisher()
    {
        ResponseModel<BookResponse> resp = new ResponseModel<>();
        try
        {
            BookService sv = new BookService();
            List<Publisher> result = sv.getPublisherList();
            BookResponse bookResp = new BookResponse();
            bookResp.setPublishers(result);

            resp.setResult(bookResp);
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
            resp.setAction("GetPublisher");
            sendResponse(resp);
        }
    }

    public void getAuthor()
    {
        ResponseModel<BookResponse> resp = new ResponseModel<>();
        try
        {
            BookService sv = new BookService();
            List<Author> result = sv.getAuthorList();
            BookResponse bookResp = new BookResponse();
            bookResp.setAuthors(result);
            resp.setResult(bookResp);

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
            resp.setAction("GetAuthor");
            sendResponse(resp);
        }
    }

    public void searchBooks(String xmlString)
    {
        ResponseModel<BookResponse> resp = new ResponseModel<>();
        BookResponse bookResp = new BookResponse();
        try
        {
            RequestModel<BookRequest> bookReq = (RequestModel<BookRequest>) utilsReq.convertXmlToObject(xmlString);
            
            Book dto = bookReq.getData().getBook();
            BookService sv = new BookService();
            List<Book> books = sv.searchBooks(dto.getName(), dto.getAuthorId().get(0));
            bookResp.setBooks(books);
            resp.setResult(bookResp);
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
            resp.setAction("Search");
            sendResponse(resp);
        }
    }

    private void sendResponse(ResponseModel<BookResponse> resp)
    {
        String responseStr = utilsResponse.convertObjectToXml(resp);
        PrintWriter writer = new PrintWriter(os, true);
        writer.println(responseStr);
    }
}
