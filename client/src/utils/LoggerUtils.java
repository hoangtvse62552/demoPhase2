package utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LoggerUtils
{
    /**
     * method to show an info alert
     * 
     * @param msg: message
     */
    public static void alert(JFrame frame, String msg)
    {
        JOptionPane.showMessageDialog(frame.getRootPane(), msg);
    }

    /**
     * method to show an error alert
     * 
     * @param msg: message
     */
    public static void alert(JFrame frame, String msg, String title)
    {
        JOptionPane.showMessageDialog(frame.getRootPane(), msg, title, JOptionPane.ERROR_MESSAGE);
    }
}
