package request;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import model.Book;

@XmlRootElement(name = "BookRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookRequest extends RequestModel
{
    @XmlElement(name = "book")
    private Book book;

    public void setBook(Book book)
    {
        this.book = book;
    }

}
