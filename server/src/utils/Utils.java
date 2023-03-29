package utils;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import request.AccountRequest;
import request.BookRequest;
import request.PingRequest;
import request.RequestModel;
import response.AccountResponse;
import response.BookResponse;
import response.PingResponse;
import response.ResponseModel;

public class Utils<T>
{

    public Object convertXmlToObject(String xmlString)
    {
        Object request = null;
        try
        {
            StringReader reader = new StringReader(xmlString);
            JAXBContext context = JAXBContext.newInstance(RequestModel.class, AccountRequest.class, 
                    BookRequest.class, ResponseModel.class, AccountResponse.class, 
                    BookResponse.class, PingRequest.class, PingResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            request = (Object) unmarshaller.unmarshal(reader);

        }
        catch (Exception e)
        {
            System.out.println("Error convert xml to request: " + e);
        }
        return request;
    }

    public String convertObjectToXml(Object input)
    {
        String xmlResult = "";
        try
        {
            JAXBContext context = JAXBContext.newInstance(ResponseModel.class, RequestModel.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshaller.marshal(input, sw);
            xmlResult = sw.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return xmlResult;
    }
}
