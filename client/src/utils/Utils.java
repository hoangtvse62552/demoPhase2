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
import request.RequestModel;
import response.AccountResponse;
import response.BookResponse;
import response.ResponseModel;

public class Utils
{
    public String convertRequestToXml(RequestModel input)
    {
        String xmlResult = "";
        try
        {
            JAXBContext context;
            if (input instanceof AccountRequest)
            {
                input = (AccountRequest) input;
                context = JAXBContext.newInstance(AccountRequest.class);
            }
            else
            {
                input = (BookRequest) input;
                context = JAXBContext.newInstance(BookRequest.class);
            }

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

    public ResponseModel convertXmlToResponse(String xmlString)
    {
        ResponseModel response = null;
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
            Element root = document.getDocumentElement();

            NodeList actionNode = root.getElementsByTagName("AccountResponse");

            JAXBContext context;
            if (actionNode.item(0) != null)
            {
                context = JAXBContext.newInstance(AccountResponse.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (AccountResponse) unmarshaller.unmarshal(root);
            }
            else
            {
                context = JAXBContext.newInstance(BookResponse.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                response = (BookResponse) unmarshaller.unmarshal(root);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error convert xml to response: " + e);
        }
        return response;
    }
}
