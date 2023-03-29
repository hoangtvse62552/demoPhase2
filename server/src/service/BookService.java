package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logger.ServerLogger;
import model.Author;
import model.Book;
import model.Publisher;
import utils.TransactionManager;

public class BookService
{

    private Connection               con;
    private PreparedStatement        stm;
    private ResultSet                rs;
    private Map<Integer, String>     publisher          = new HashMap<>();
    private Map<Integer, String>     authors            = new HashMap<>();
    private List<Publisher>          publisherList;
    private List<Author>             authorList;
    private Map<Integer, Book>       bookMap;
    private final TransactionManager transactionManager = TransactionManager.getInstance();

    public BookService()
    {
        getPublisher();
        getAuthors();
    }

    public List<Book> getBooks()
    {
        List<Book> books = new ArrayList<>();

        try
        {
            con = transactionManager.getTransaction();
            String sql = "SELECT * FROM BOOKS";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next())
            {
                int bookId = rs.getInt("book_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int publisherId = rs.getInt("publisher_id");
                String author = "";
                List<Integer> authorId = getAuthorOfBook(bookId);
                if (authorId.size() > 0)
                {
                    for (Integer id : authorId)
                    {
                        author += authors.get(id) + " ";

                    }
                }
                Book dto = new Book(bookId, name, publisher.get(publisherId), author, description);
                dto.setPublisherId(publisherId);
                dto.setAuthorId(authorId);
                books.add(dto);
            }
        }
        catch (SQLException e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            transactionManager.closeConnection(con, stm, rs);
        }
        return books;
    }

    private void getPublisher()
    {
        publisherList = new ArrayList<>();
        try
        {
            con = transactionManager.getTransaction();
            String sql = "SELECT * FROM PUBLISHERS";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("publisher_id");
                String name = rs.getString("name");
                publisher.put(id, name);
                Publisher dto = new Publisher(id, name);
                publisherList.add(dto);
            }
        }
        catch (SQLException e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            transactionManager.closeConnection(con, stm, rs);
        }
    }

    private void getAuthors()
    {
        authorList = new ArrayList<>();
        try
        {
            con = transactionManager.getTransaction();
            String sql = "SELECT * FROM AUTHORS";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("author_id");
                String name = rs.getString("name");
                authors.put(id, name);
                Author dto = new Author(id, name);
                authorList.add(dto);
            }
        }
        catch (SQLException e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            transactionManager.closeConnection(con, stm, rs);
        }
    }

    private List<Integer> getAuthorOfBook(int bookId)
    {
        List<Integer> authorsId = new ArrayList<>();
        try
        {
            con = transactionManager.getTransaction();
            String sql = "SELECT author_id FROM BOOK_AUTHORS WHERE BOOK_ID = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, bookId);
            rs = stm.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("author_id");
                authorsId.add(id);
            }
        }
        catch (SQLException e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            transactionManager.closeConnection(con, stm, rs);
        }

        return authorsId;
    }

    public boolean updateBook(Book book)
    {
        try
        {
            con = transactionManager.getTransaction();
            String sql = "UPDATE BOOKS SET NAME= ?, DESCRIPTION= ?, UPDATE_TIME= ?, PUBLISHER_ID= ? WHERE BOOK_ID= ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, book.getName());
            stm.setString(2, book.getDescription());
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            stm.setString(3, timeStamp);
            stm.setInt(4, book.getPublisherId());
            stm.setInt(5, book.getId());
            int rs = stm.executeUpdate();
            if (rs != 0)
            {
                String sql1 = "DELETE FROM BOOK_AUTHORS WHERE BOOK_ID = ?";
                stm = con.prepareStatement(sql1);
                stm.setInt(1, book.getId());
                rs = stm.executeUpdate();
                System.out.println(book.getAuthorId().size());
                if (rs != 0)
                {
                    for (Integer id : book.getAuthorId())
                    {
                        String sql2 = "INSERT INTO BOOK_AUTHORS (AUTHOR_ID, BOOK_ID) VALUES(?, ?)";
                        stm = con.prepareStatement(sql2);
                        stm.setInt(1, id);
                        stm.setInt(2, book.getId());
                        rs = stm.executeUpdate();
                        if (rs == 0)
                        {
                            return false;
                        }
                    }
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            transactionManager.closeConnection(con, stm, rs);
        }

        return true;
    }

    public boolean addBook(Book book)
    {
        System.out.println("Add book controller");
        boolean check = false;
        long bookId = -1;
        try
        {
            con = transactionManager.getTransaction();
            String sql = "INSERT INTO BOOKS (NAME, DESCRIPTION, CREATE_TIME, UPDATE_TIME, PUBLISHER_ID) VALUES( ?, ?, ?, ?, ?)";
            stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, book.getName());
            stm.setString(2, book.getDescription());
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            stm.setString(3, timeStamp);
            stm.setString(4, timeStamp);
            stm.setInt(5, book.getPublisherId());
            int x = stm.executeUpdate();
            if (x != 0)
            {
                rs = stm.getGeneratedKeys();
                if (rs.next())
                {
                    bookId = rs.getLong("book_id");
                    for (Integer id : book.getAuthorId())
                    {
                        String sql1 = "INSERT INTO BOOK_AUTHORS (AUTHOR_ID, BOOK_ID) VALUES(?, ?)";
                        stm = con.prepareStatement(sql1);
                        stm.setInt(1, id);
                        stm.setLong(2, bookId);
                        int rs = stm.executeUpdate();
                        if (rs == 0)
                        {
                            return false;
                        }
                    }
                    check = true;
                }
            }
        }
        catch (SQLException e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            transactionManager.closeConnection(con, stm, rs);
        }

        return check;
    }

    public boolean deleteBook(int id)
    {
        try
        {
            con = transactionManager.getTransaction();
            String sql = "DELETE FROM BOOKS WHERE BOOK_ID= ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            int rs = stm.executeUpdate();
            if (rs != 0)
            {
                return true;
            }
        }
        catch (Exception e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            transactionManager.closeConnection(con, stm, rs);
        }
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
        List<Book> books = new ArrayList<>();
        bookMap = new HashMap<>();
        try
        {
            con = transactionManager.getTransaction();
            String sql = "";

            if (authorId != -1)
            {
                sql = "SELECT * , k.NAME AS author_name \r\n" + "FROM BOOKS b \r\n" + "LEFT JOIN BOOK_AUTHORS o ON b.BOOK_ID = o.BOOK_ID \r\n" + "LEFT JOIN AUTHORS k ON k.AUTHOR_ID = o.AUTHOR_ID \r\n" + "WHERE (b.BOOK_ID  IN  (SELECT BOOK_ID  FROM BOOK_AUTHORS z WHERE z.AUTHOR_ID = ?)) \r\n" + "AND b.NAME LIKE ?\r\n" + "ORDER BY b.BOOK_ID ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, authorId);
                stm.setString(2, "%" + searchString + "%");
            }
            else
            {
                sql = "SELECT *, k.NAME AS author_name FROM BOOKS b LEFT JOIN BOOK_AUTHORS o ON b.BOOK_ID = o.BOOK_ID LEFT JOIN AUTHORS k ON k.AUTHOR_ID = o.AUTHOR_ID WHERE b.NAME LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchString + "%");
            }

            rs = stm.executeQuery();
            if (rs != null)
            {
                while (rs.next())
                {
                    int bookId = rs.getInt("book_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    int publisherId = rs.getInt("publisher_id");
                    String author = rs.getString("author_name");
                    int authorId1 = rs.getInt("author_id");
                    List<Integer> authorIds = new ArrayList<>();
                    authorIds.add(authorId1);

                    if (bookMap.containsKey(bookId))
                    {
                        String authorName = bookMap.get(bookId).getAuthor() + " " + author;
                        bookMap.get(bookId).setAuthor(authorName);
                        authorIds = bookMap.get(bookId).getAuthorId();
                        authorIds.add(authorId1);
                        bookMap.get(bookId).setAuthorId(authorIds);
                    }
                    else
                    {
                        Book dto = new Book(bookId, name, publisher.get(publisherId), author, description);
                        dto.setPublisherId(publisherId);
                        dto.setAuthorId(authorIds);
                        bookMap.put(bookId, dto);
                    }
                }
                for (Map.Entry<Integer, Book> book : bookMap.entrySet())
                {
                    books.add(book.getValue());
                }
            }
        }
        catch (Exception e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            transactionManager.closeConnection(con, stm, rs);
        }
        return books;
    }

    public Map<Integer, String> getAuthorMap()
    {
        return authors;
    }

    public List<Book> getBooksVer2()
    {
        List<Book> books = new ArrayList<>();
        bookMap = new HashMap<>();
        try
        {
            con = transactionManager.getTransaction();
            String sql = "SELECT *, k.NAME AS author_name FROM BOOKS b LEFT JOIN BOOK_AUTHORS o ON b.BOOK_ID = o.BOOK_ID LEFT JOIN AUTHORS k ON k.AUTHOR_ID = o.AUTHOR_ID";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next())
            {
                int bookId = rs.getInt("book_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int publisherId = rs.getInt("publisher_id");
                String author = rs.getString("author_name");
                int authorId = rs.getInt("author_id");
                List<Integer> authorIds = new ArrayList<>();
                authorIds.add(authorId);

                if (bookMap.containsKey(bookId))
                {
                    String authorName = bookMap.get(bookId).getAuthor() + " " + author;
                    bookMap.get(bookId).setAuthor(authorName);
                    authorIds = bookMap.get(bookId).getAuthorId();
                    authorIds.add(authorId);
                    bookMap.get(bookId).setAuthorId(authorIds);
                }
                else
                {
                    Book dto = new Book(bookId, name, publisher.get(publisherId), author, description);
                    dto.setPublisherId(publisherId);
                    dto.setAuthorId(authorIds);
                    bookMap.put(bookId, dto);
                }
            }
            for (Map.Entry<Integer, Book> book : bookMap.entrySet())
            {
                books.add(book.getValue());
            }
        }
        catch (SQLException e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            transactionManager.closeConnection(con, stm, rs);
        }
        return books;
    }
}
