package response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AccountResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountResponse extends ResponseModel
{
    @XmlElement(name = "isAdmin")
    private boolean isAdmin;

    public void setAdmin(boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

}
