package controller;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import model.Book;
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

        }
        catch (Exception e)
        {
            e.printStackTrace();
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
