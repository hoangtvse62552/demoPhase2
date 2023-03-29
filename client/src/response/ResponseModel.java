package response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "response")
@XmlSeeAlso({ AccountResponse.class, BookResponse.class, PingResponse.class })
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseModel<D>
{
    @XmlElement(name = "action")
    private String action;

    @XmlElement(name = "status")
    private String status;

    @XmlElement(name = "error")
    private String error;

    @XmlElement(name = "result")
    private D      result;

    public D getResult()
    {
        return result;
    }

    public void setResult(D result)
    {
        this.result = result;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public String getStatus()
    {
        return status;
    }

    public String getError()
    {
        return error;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

}
