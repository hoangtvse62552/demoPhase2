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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableCellRenderer;

import controller.BookController;
//import controller.BookController;
import controller.DirectController;
import model.*;
import ui.model.TableModel;
import utils.LoggerUtils;

import java.awt.Component;

public class HomePage extends JFrame
{
    /**
     * 
     */
    private static final long            serialVersionUID = 1L;
    private List<Book>                   books;
    private JTable                       tblBook;
    private TableModel                   tableModel;
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
    private JButton                      btnUpdate;
    private Map<String, Integer>         authorsMap;
    private Map<String, Integer>         publisherMap;

    private Book                         currentEdit;
    private boolean                      isAdmin          = false;
    private JButton                      btnLogout;
    private JLabel                       error;

    private JTextField                   txtSearch;
    private JButton                      btnSearch;
    private CheckBoxRenderer             authorRenderer;

    private JComboBox<String>            cbAuthorFilter;
    private DefaultComboBoxModel<String> cbAuthorModelFilter;
    private DirectController             directController = DirectController.getInstance();
    private JButton                      btnDelete;

    private JLabel                       lbName;
    private JLabel                       lbAuthor;
    private JLabel                       lbPublisher;
    private JLabel                       lbDescription;

    /**
     * Create the frame.
     */
    public HomePage(boolean isAdmin)
    {
        this.isAdmin = isAdmin;
        sv = new BookController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
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
        tableModel = new TableModel();
        tblBook = new JTable(tableModel);
        tblBook.setAutoCreateRowSorter(true);

        // Table show books
        JScrollPane pane = new JScrollPane(tblBook);
        pane.setBounds(0, 20, 800, 230);

        // add book layout
        lbName = new JLabel("Name");
        lbName.setBounds(150, 260, 80, 20);
        lbAuthor = new JLabel("Author");
        lbAuthor.setBounds(150, 290, 80, 20);
        lbPublisher = new JLabel("Publisher");
        lbPublisher.setBounds(150, 320, 80, 20);
        lbDescription = new JLabel("Description");
        lbDescription.setBounds(150, 350, 80, 20);

        txtName = new JTextField();
        txtName.setBounds(240, 260, 200, 20);

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
        cbAuthor.setBounds(240, 290, 200, 20);

        cbPublisherModel = new DefaultComboBoxModel<String>();
        cbPublisher = new JComboBox<>();
        cbPublisher.setModel(cbPublisherModel);
        cbPublisher.setBounds(240, 320, 200, 20);
        txtDescription = new JTextArea();
        txtDescription.setBounds(240, 350, 200, 50);

        btnCreate = new JButton("Add");
        btnCreate.setBounds(500, 260, 80, 20);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(500, 300, 80, 20);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(500, 340, 80, 20);

        error = new JLabel("Error");
        error.setBounds(300, 430, 200, 20);
        error.setVisible(false);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(300, 450, 80, 20);

        setVisibleLayout(isAdmin);
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

        add(btnUpdate);
        add(btnDelete);

        add(btnLogout);
        add(error);
        getBooks();
        addListner();
        initData();
    }

    private void setVisibleLayout(boolean x)
    {
        lbName.setVisible(x);
        txtName.setVisible(x);
        lbAuthor.setVisible(x);
        cbAuthor.setVisible(x);
        lbPublisher.setVisible(x);
        cbPublisher.setVisible(x);
        lbDescription.setVisible(x);
        txtDescription.setVisible(x);
        btnUpdate.setVisible(x);
        btnCreate.setVisible(x);
        btnDelete.setVisible(x);
        tblBook.setEnabled(x);
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
            cbAuthorModelFilter.addElement(authors.get(x).getName());
            authorString.add(authors.get(x).getName());
            authorsMap.put(authors.get(x).getName(), authors.get(x).getId());
        }
        cbAuthorModelFilter.addElement("All");
        for (int x = 0; x < publisher.size(); x++)
        {
            cbPublisherModel.addElement(publisher.get(x).getName());
            publisherMap.put(publisher.get(x).getName(), publisher.get(x).getId());
        }

        authorRenderer = new CheckBoxRenderer(authorString);
        cbAuthor.setRenderer(authorRenderer);
        cbAuthor.addItemListener(e -> {
            String item = (String) e.getItem();
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                authorRenderer.setSelected(item);
            }
        });
    }

    private void getBooks()
    {
        books = sv.getBooks();
        tableModel.initData(books);
    }

    private void addListner()
    {
        btnLogout.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                directController.logout();
            }
        });
        btnUpdate.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int bookId = currentEdit.getId();
                String name = txtName.getText().trim();
                if (name.length() > 0)
                {
                    List<Integer> authorIds = new ArrayList<>();
                    List<String> authorName = authorRenderer.getListSelected();
                    for (String string : authorName)
                    {
                        authorIds.add(authorsMap.get(string));
                        System.out.println("Author Id: " + authorsMap.get(string));
                    }

                    int publisherId = publisher.get(cbPublisher.getSelectedIndex()).getId();
                    String description = txtDescription.getText();
                    Book dto = new Book(bookId, name, "", "", description);
                    dto.setPublisherId(publisherId);
                    dto.setAuthorId(authorIds);
                    boolean rs = sv.updateBook(dto);
                    if (rs)
                    {
                        System.out.println("Update Book successful");
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
                        System.out.println("Add new book successful");
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
                    tableModel.initData(books);
                }
            }
        });
        btnDelete.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                int option = LoggerUtils.alertConfirm(directController.getHomePage(), "Are u sure about that", "");
                if (option == JOptionPane.YES_OPTION)
                {
                    boolean rs = sv.deleteBook(currentEdit.getId());
                    if (rs)
                    {
                        getBooks();
                    }
                }
            }
        });
        tblBook.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseReleased(MouseEvent e)
            {

            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (isAdmin)
                {
                    int row = e.getY() / tblBook.getRowHeight();
                    currentEdit = books.get(row);
                    txtName.setText(books.get(row).getName());

                    List<Integer> authorIds = books.get(row).getAuthorId();
                    Map<Integer, String> authorMap = sv.getAuthorMap();
                    List<String> authorsName = new ArrayList<>();
                    for (Integer id : authorIds)
                    {
                        authorsName.add(authorMap.get(id));
                    }
                    authorRenderer.setSelectedList(authorsName);
                    cbPublisher.setSelectedIndex(publisherMap.get(books.get(row).getPublisher().trim()));
                    txtDescription.setText(books.get(row).getDescription());
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {

            }

            @Override
            public void mouseEntered(MouseEvent e)
            {

            }

            @Override
            public void mouseClicked(MouseEvent e)
            {

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
