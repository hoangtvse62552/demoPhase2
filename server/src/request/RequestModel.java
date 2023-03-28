package request;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "request")
@XmlSeeAlso({ AccountRequest.class, BookRequest.class })
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestModel<D>
{
    @XmlElement(name = "action")
    private String action;

    @XmlElement(name = "data")
    private D      data;

    public D getData()
    {
        return data;
    }

    public void setData(D data)
    {
        this.data = data;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getAction()
    {
        return action;
    }

}
