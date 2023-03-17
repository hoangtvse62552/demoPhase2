package test;

import response.AccountResponse;
import utils.XmlUtils;

public class TestConvertXml
{
    public static void main(String[] args)
    {
        XmlUtils util = new XmlUtils();
        AccountResponse accountResponse = (AccountResponse) util.convertXmlToResponse("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<AccountResponse>" + "    <status>success</status>" + "    <isAdmin>false</isAdmin>" + "</AccountResponse>");
        if (accountResponse != null)
        {
            System.out.println("success");
        }
    }
}
