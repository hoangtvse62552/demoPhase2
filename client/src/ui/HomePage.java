package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.BookController;
import controller.DirectController;
import model.*;

import java.awt.Component;

public class HomePage extends JFrame
{
    /**
     * 
     */
    private static final long            serialVersionUID = 1L;
    private List<Book>                   books;
    private JTable                       tblBook;
    private DefaultTableModel            model;
    private JButton                      btnCreate;
    private BookController               sv;

    private JTextField                   txtName;
    private JComboBox<String>            cbAuthor;
    private JComboBox<String>            cbPublisher;
    private JTextArea                    txtDescription;
    private DefaultComboBoxModel<String> cbAuthorModel;
    private DefaultComboBoxModel<String> cbPublisherModel;

    private List<Author>                 authors;
    private List<Publisher>              publisher;

    private JTextField                   txtNameEdit;
    private JComboBox<String>            cbAuthorEdit;
    private JComboBox<String>            cbPublisherEdit;
    private JTextArea                    txtDescriptionEdit;
    private JButton                      btnSave;
    private JLabel                       lbNameEdit;
    private JLabel                       lbAuthorEdit;
    private JLabel                       lbPublisherEdit;
    private JLabel                       lbDescriptionEdit;
    private DefaultComboBoxModel<String> cbAuthorModelEdit;
    private DefaultComboBoxModel<String> cbPublisherModelEdit;

    private Map<String, Integer>         authorsMap;
    private Map<String, Integer>         publisherMap;

    private Book                         currentEdit;
    private boolean                      isAdmin          = false;
    private LoginPage                    loginPage;
    private JButton                      btnLogout;
    private JLabel                       error;

    private JTextField                   txtSearch;
    private JButton                      btnSearch;
    private CheckBoxRenderer             authorRenderer;
    private CheckBoxRenderer             authorEditRenderer;

    private JComboBox<String>            cbAuthorFilter;
    private DefaultComboBoxModel<String> cbAuthorModelFilter;
    private DirectController             controller;

    /**
     * Create the frame.
     */
    public HomePage(boolean isAdmin, DirectController controller)
    {
        this.isAdmin = isAdmin;
        this.controller = controller;
        sv = new BookController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
//        setSize(800, 550);
        setBounds(300, 300, 800, 550);

        // search layout
        txtSearch = new JTextField();
        txtSearch.setBounds(0, 0, 600, 20);

        cbAuthorFilter = new JComboBox<String>();
        cbAuthorModelFilter = new DefaultComboBoxModel<>();
        cbAuthorFilter.setModel(cbAuthorModelFilter);
        cbAuthorFilter.setBounds(600, 0, 100, 20);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(700, 0, 100, 20);
        // -------------------------------------
        model = new DefaultTableModel();
        tblBook = new JTable(model);
        tblBook.setEnabled(false);
        // Table show books
        JScrollPane pane = new JScrollPane(tblBook);
        pane.setBounds(0, 20, 800, 220);

        // add book layout
        JLabel lbName = new JLabel("Name");
        lbName.setBounds(10, 260, 80, 20);
        JLabel lbAuthor = new JLabel("Author");
        lbAuthor.setBounds(10, 290, 80, 20);
        JLabel lbPublisher = new JLabel("Publisher");
        lbPublisher.setBounds(10, 320, 80, 20);
        JLabel lbDescription = new JLabel("Description");
        lbDescription.setBounds(10, 350, 80, 20);

        txtName = new JTextField();
        txtName.setBounds(100, 260, 200, 20);

        cbAuthorModel = new DefaultComboBoxModel<String>();
        cbAuthor = new JComboBox<String>()
        {

            @Override
            public void setPopupVisible(boolean v)
            {
                if (v)
                    super.setPopupVisible(v);
            }

        };
        cbAuthor.setModel(cbAuthorModel);
        cbAuthor.setBounds(100, 290, 200, 20);

        cbPublisherModel = new DefaultComboBoxModel<String>();
        cbPublisher = new JComboBox<>();
        cbPublisher.setModel(cbPublisherModel);
        cbPublisher.setBounds(100, 320, 200, 20);
        txtDescription = new JTextArea();
        txtDescription.setBounds(100, 350, 200, 50);

        btnCreate = new JButton("Add");
        btnCreate.setBounds(100, 410, 80, 20);
        btnCreate.setEnabled(isAdmin);

        // Edit book layout
        lbNameEdit = new JLabel("Name");
        lbNameEdit.setBounds(350, 260, 80, 20);
        lbAuthorEdit = new JLabel("Author");
        lbAuthorEdit.setBounds(350, 290, 80, 20);
        lbPublisherEdit = new JLabel("Publisher");
        lbPublisherEdit.setBounds(350, 320, 80, 20);
        lbDescriptionEdit = new JLabel("Description");
        lbDescriptionEdit.setBounds(350, 350, 80, 20);

        txtNameEdit = new JTextField();
        txtNameEdit.setBounds(450, 260, 200, 20);

        cbAuthorModelEdit = new DefaultComboBoxModel<String>();
        cbAuthorEdit = new JComboBox<>()
        {

            @Override
            public void setPopupVisible(boolean v)
            {
                if (v)
                    super.setPopupVisible(v);
            }

        };
        ;
        cbAuthorEdit.setModel(cbAuthorModelEdit);
        cbAuthorEdit.setBounds(450, 290, 200, 20);

        cbPublisherModelEdit = new DefaultComboBoxModel<String>();
        cbPublisherEdit = new JComboBox<>();
        cbPublisherEdit.setModel(cbPublisherModelEdit);
        cbPublisherEdit.setBounds(450, 320, 200, 20);
        txtDescriptionEdit = new JTextArea();
        txtDescriptionEdit.setBounds(450, 350, 200, 50);

        btnSave = new JButton("Save");
        btnSave.setBounds(450, 410, 80, 20);

        error = new JLabel("Error");
        error.setBounds(300, 430, 200, 20);
        error.setVisible(false);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(300, 450, 80, 20);

        add(txtSearch);
        add(cbAuthorFilter);
        add(btnSearch);

        add(pane);

        add(lbName);
        add(txtName);
        add(lbAuthor);
        add(cbAuthor);
        add(lbPublisher);
        add(cbPublisher);
        add(lbDescription);
        add(txtDescription);
        add(btnCreate);

        add(lbNameEdit);
        add(txtNameEdit);
        add(lbAuthorEdit);
        add(cbAuthorEdit);
        add(lbPublisherEdit);
        add(cbPublisherEdit);
        add(lbDescriptionEdit);
        add(txtDescriptionEdit);
        add(btnSave);

        add(btnLogout);
        add(error);
        setVisibleLayout(false);
        getBooks();
        addListner();
        initData();
    }

    private void setVisibleLayout(boolean x)
    {
        lbNameEdit.setVisible(x);
        txtNameEdit.setVisible(x);
        lbAuthorEdit.setVisible(x);
        cbAuthorEdit.setVisible(x);
        lbPublisherEdit.setVisible(x);
        cbPublisherEdit.setVisible(x);
        lbDescriptionEdit.setVisible(x);
        txtDescriptionEdit.setVisible(x);
        btnSave.setVisible(x);
    }

    private void initData()
    {
        authors = sv.getAuthorList();
        publisher = sv.getPublisherList();
        authorsMap = new HashMap<>();
        publisherMap = new HashMap<>();
        List<String> authorString = new ArrayList<>();

        for (int x = 0; x < authors.size(); x++)
        {
            cbAuthorModel.addElement(authors.get(x).getName());
            cbAuthorModelEdit.addElement(authors.get(x).getName());
            cbAuthorModelFilter.addElement(authors.get(x).getName());
            authorString.add(authors.get(x).getName());
            authorsMap.put(authors.get(x).getName(), authors.get(x).getId());
        }
        cbAuthorModelFilter.addElement("All");
        for (int x = 0; x < publisher.size(); x++)
        {
            cbPublisherModel.addElement(publisher.get(x).getName());
            cbPublisherModelEdit.addElement(publisher.get(x).getName());
            publisherMap.put(publisher.get(x).getName(), publisher.get(x).getId());
        }

        authorRenderer = new CheckBoxRenderer(authorString);
        cbAuthor.setRenderer(authorRenderer);
        cbAuthor.addItemListener(e -> {
            String item = (String) e.getItem();
//            System.out.println("Event click: " + e.paramString());
//            System.out.println("----------------------");
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                authorRenderer.setSelected(item);
            }

        });
//        cbAuthor.addItemListener(new ItemListener()
//        {
//
//            @Override
//            public void itemStateChanged(ItemEvent e)
//            {
//                // TODO Auto-generated method stub
//                System.out.println("Event: " + e.getStateChange());
//            }
//        });

        authorEditRenderer = new CheckBoxRenderer(authorString);
        cbAuthorEdit.setRenderer(authorEditRenderer);
        cbAuthorEdit.addItemListener(e -> {
            String item = (String) e.getItem();
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                authorEditRenderer.setSelected(item);
            }
        });
    }

    private void getBooks()
    {

        books = sv.getBooks();
        initTable();
    }

    private void initTable()
    {
        for (int i = model.getRowCount() - 1; i >= 0; i--)
        {
            model.removeRow(i);
        }

        String[] columnName = { "BookId", "Name", "Author", "Publisher", "Description", "Edit", "Delete" };
        model.setColumnIdentifiers(columnName);
        int count = 0;
        for (Book book : books)
        {
            Object[] row = new Object[7];
            row[0] = book.getId();
            row[1] = book.getName();
            row[2] = book.getAuthor();
            row[3] = book.getPublisher();
            row[4] = book.getDescription();
            JButton x = new JButton("Edit");
            x.setEnabled(isAdmin);
            row[5] = x;
            JButton y = new JButton("Delete");
            y.setEnabled(isAdmin);
            row[6] = y;
            model.insertRow(count++, row);
        }

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        tblBook.getColumn("Edit").setCellRenderer(buttonRenderer);
        tblBook.getColumn("Delete").setCellRenderer(buttonRenderer);

    }

    private void addListner()
    {
        btnLogout.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.logout();
            }
        });
        btnSave.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                int bookId = currentEdit.getId();
                String name = txtNameEdit.getText().trim();
                if (name.length() > 0)
                {
                    List<Integer> authorIds = new ArrayList<>();
                    List<String> authorName = authorEditRenderer.getListSelected();
                    for (String string : authorName)
                    {
                        authorIds.add(authorsMap.get(string));
                        System.out.println("Author Id: " + authorsMap.get(string));
                    }

                    int publisherId = publisher.get(cbPublisherEdit.getSelectedIndex()).getId();
                    String description = txtDescriptionEdit.getText();
                    Book dto = new Book(bookId, name, "", "", description);
                    dto.setPublisherId(publisherId);
                    dto.setAuthorId(authorIds);
                    boolean rs = sv.updateBook(dto);
                    if (rs)
                    {
                        boolean rs1 = sv.updateAuthorBook(bookId, authorIds);
                        if (rs1)
                            System.out.println("Update Book Author successful");
                        System.out.println("Update Book successful");
                        getBooks();
                        setVisibleLayout(false);
                    }
                    error.setVisible(false);
                }
                else
                {
                    error.setText("Book name can not be empty");
                    error.setVisible(true);
                }

            }
        });
        btnCreate.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String name = txtName.getText().trim();
                if (name.length() > 0)
                {
                    List<Integer> authorIds = new ArrayList<>();
                    List<String> authorName = authorRenderer.getListSelected();
                    for (String string : authorName)
                    {
                        authorIds.add(authorsMap.get(string));
                    }

                    int publisherId = publisher.get(cbPublisher.getSelectedIndex()).getId();
                    String description = txtDescription.getText();
                    Book dto = new Book(0, name, "", "", description);
                    dto.setPublisherId(publisherId);
                    dto.setAuthorId(authorIds);
                    boolean rs = sv.addBook(dto);
                    if (rs)
                    {
                        getBooks();
                    }
                    error.setVisible(false);
                }
                else
                {
                    error.setText("Book name can not be empty");
                    error.setVisible(true);
                }

            }
        });
        btnSearch.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String searchString = txtSearch.getText().trim();
                int authorId = -1;
                if (cbAuthorFilter.getSelectedIndex() < authors.size())
                {
                    authorId = authors.get(cbAuthorFilter.getSelectedIndex()).getId();
                }
                List<Book> bookSearchs = sv.searchBooks(searchString, authorId);
                if (bookSearchs != null)
                {
                    books = bookSearchs;
                    initTable();
                }

            }
        });
        tblBook.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseReleased(MouseEvent e)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (isAdmin)
                {
                    int column = tblBook.getColumnModel().getColumnIndexAtX(e.getX());
                    int row = e.getY() / tblBook.getRowHeight();
                    if (column == 5 && (row >= 0 && row < tblBook.getRowCount()))
                    {
                        setVisibleLayout(true);
                        currentEdit = books.get(row);
                        txtNameEdit.setText(books.get(row).getName());

                        List<Integer> authorIds = books.get(row).getAuthorId();
                        Map<Integer, String> authorMap = sv.getAuthorMap();
                        List<String> authorsName = new ArrayList<>();
                        for (Integer id : authorIds)
                        {
                            authorsName.add(authorMap.get(id));
                        }
                        authorEditRenderer.setSelectedList(authorsName);
                        cbPublisherEdit.setSelectedIndex(publisherMap.get(books.get(row).getPublisher().trim()));
                        txtDescriptionEdit.setText(books.get(row).getDescription());

                    }
                    else if (column == 6 && (row >= 0 && row < tblBook.getRowCount()))
                    {
                        System.out.println("Delete button");
                        boolean rs = sv.deleteBook(Integer.parseInt(model.getValueAt(row, 0).toString()));
                        if (rs)
                        {
                            model.removeRow(row);
                        }
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e)
            {
                // TODO Auto-generated method stub

            }
        });
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

}

class JTableButtonRenderer implements TableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        JButton button = (JButton) value;
        return button;
    }
}

class CheckBoxRenderer implements ListCellRenderer<Object>
{
    private Map<String, JCheckBox> items = new HashMap<>();
    private List<JCheckBox>        boxs  = new ArrayList<>();

    public CheckBoxRenderer(List<String> items)
    {
        for (String item : items)
        {
            JCheckBox box = new JCheckBox(item);

            boxs.add(box);
            this.items.put(item, box);
        }

    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        if (items.containsKey(value))
        {
            return items.get(value);
        }
        else
        {
            return null;
        }
    }

    public List<String> getListSelected()
    {
        List<String> authorsName = new ArrayList<>();
        for (JCheckBox jCheckBox : boxs)
        {
            if (jCheckBox.isSelected())
            {
                authorsName.add(jCheckBox.getText());
            }

        }
        return authorsName;
    }

    public void setSelectedList(List<String> nameList)
    {
        for (JCheckBox box : boxs)
        {
            box.setSelected(false);
        }
        for (String name : nameList)
        {
            if (items.containsKey(name))
                items.get(name).setSelected(true);
        }
    }

    public void setSelected(String item)
    {
        if (item.contains(item))
        {
            JCheckBox cb = items.get(item);
            cb.setSelected(!cb.isSelected());
        }
    }
}
