package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account
{

    @XmlElement(name = "id")
    private String  id;

    @XmlElement(name = "username")
    private String  username;

    @XmlElement(name = "password")
    private String  password;

    @XmlElement(name = "isAdmin")
    private boolean isAdmin;

    public Account()
    {
        super();
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Account(String id, String username, String password)
    {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
    }

}