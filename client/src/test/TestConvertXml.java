package test;

import response.AccountResponse;
import utils.Utils;

public class TestConvertXml
{
    public static void main(String[] args)
    {
        Utils util = new Utils();
        AccountResponse accountResponse = (AccountResponse) util.convertXmlToResponse("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + "<AccountResponse>\n" + "    <status>success</status>\n" + "    <isAdmin>false</isAdmin>\n" + "</AccountResponse>");
        if (accountResponse != null)
        {
            System.out.println("success");
        }
    }
}
