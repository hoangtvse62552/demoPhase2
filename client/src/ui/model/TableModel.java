package ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.Book;

public class TableModel extends DefaultTableModel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    public void initData(List<Book> books)
    {
        for (int i = this.getRowCount() - 1; i >= 0; i--)
        {
            this.removeRow(i);
        }
        String[] columnName = { "BookId", "Name", "Author", "Publisher", "Description" };
        this.setColumnIdentifiers(columnName);
        int count = 0;
        for (Book book : books)
        {
            Object[] row = new Object[7];
            row[0] = book.getId();
            row[1] = book.getName();
            row[2] = book.getAuthor();
            row[3] = book.getPublisher();
            row[4] = book.getDescription();
            this.insertRow(count++, row);
        }
    }
}
