package model;

import java.util.List;

public class Book
{
    private int           id;
    private String        name;
    private String        publisher;
    private String        author;
    private String        description;
    private int           publisherId;
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

    @Override
    public String toString()
    {
        return "Book [id=" + id + ", name=" + name + ", publisher=" + publisher + ", author=" + author + ", description=" + description + "]";
    }

}
