package model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.FIELD)
public class Book
{
    @XmlElement(name = "id")
    private int           id;

    @XmlElement(name = "name")
    private String        name;

    @XmlElement(name = "publisher")
    private String        publisher;

    @XmlElement(name = "author")
    private String        author;

    @XmlElement(name = "description")
    private String        description;

    @XmlElement(name = "publisherId")
    private int           publisherId;

    @XmlElement(name = "authorId")
    private List<Integer> authorId;

    public List<Integer> getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(List<Integer> authorId)
    {
        this.authorId = authorId;
    }

    public int getPublisherId()
    {
        return publisherId;
    }

    public void setPublisherId(int publisherId)
    {
        this.publisherId = publisherId;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
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

    public Book(int id, String name, String publisher, String author, String description)
    {
        super();
        this.id = id;
        this.name = name;
        this.publisher = publisher;
        this.author = author;
        this.description = description;
    }

    public Book()
    {
        super();
    }

    @Override
    public String toString()
    {
        return "Book [id=" + id + ", name=" + name + ", publisher=" + publisher + ", author=" + author + ", description=" + description + "]";
    }

}
