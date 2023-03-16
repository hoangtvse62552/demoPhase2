package response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseModel
{
    @XmlElement(name = "status")
    private String status;

    @XmlElement(name = "error")
    private String error;

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


}
