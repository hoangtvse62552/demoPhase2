package response;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import model.*;

@XmlRootElement(name = "BookResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookResponse extends ResponseModel
{
    @XmlElement(name = "book")
    private List<Book>      books;

    @XmlElement(name = "publisher")
    private List<Publisher> publishers;

    @XmlElement(name = "author")
    private List<Author>    authors;

    public void setBooks(List<Book> books)
    {
        this.books = books;
    }

    public BookResponse()
    {
        super();
    }

    public void setPublishers(List<Publisher> publishers)
    {
        this.publishers = publishers;
    }

    public void setAuthors(List<Author> authors)
    {
        this.authors = authors;
    }

    public List<Book> getBooks()
    {
        return books;
    }

    public List<Publisher> getPublishers()
    {
        return publishers;
    }

    public List<Author> getAuthors()
    {
        return authors;
    }

}
