package request;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AccountRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountRequest extends RequestModel
{
    @XmlElement(name = "username")
    private String username;

    @XmlElement(name = "password")
    private String password;

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
