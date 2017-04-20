package view;


import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import events.*;
import events.Event;
import model.AddressBook;
import model.Record;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;


public class MainView extends JFrame {
    private JButton deleteButton;
    private JButton addButton;
    private JTable table;
    private JScrollPane scroll;
    private JTextField tfname;
    private JTextField tfsurname;
    private JTextField tfaddress;
    private JTextField tfemail;
    private JTextField tfphone;
    private JTextField tfjob;
    private JLabel labname;
    private JLabel labsurname;
    private JLabel labaddress;
    private JLabel labemail;
    private JLabel labphone;
    private JLabel labjob;
    private JPanel panel;
    private JButton clearButton;


    private AddressBook book;
    private EventBus eventBus;

    public MainView(AddressBook book, final EventBus eventBus){
        this.book = book;
        this.eventBus = eventBus;
        init();
        initElements();
        eventBus.addEventListener(BookChangedEvent.EVENT_TYPE, new Listener() {
            @Override
            public void perform(Event a) {
                table.revalidate();
                table.repaint();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.dispatchEvent(new SaveBookEvent());
                System.exit(0);
            }
        });
    }

    public void init(){
        setBounds(400,10,600,700);
        setVisible(true);
        setTitle("Адресная книга");
        setResizable(false);
        setLayout(null);
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());

        }catch (UnsupportedLookAndFeelException exp){
            exp.printStackTrace();
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void initElements(){

        //Выделение памяти под компоненты
        tfaddress = new JTextField();
        tfemail = new JTextField();
        tfjob = new JTextField();
        tfphone = new JTextField();
        tfname = new JTextField();
        tfsurname = new JTextField();
        labaddress = new JLabel("Адрес: ");
        labemail = new JLabel("Email: ");
        labjob = new JLabel("Работа: ");
        labname = new JLabel("Имя: ");
        labphone = new JLabel("Телефон: ");
        labsurname = new JLabel("Фамилия: ");
        deleteButton = new JButton("Удалить");
        addButton = new JButton("Добавить");
        clearButton = new JButton("Очистить");
        panel = new JPanel();

        //Таблица и её модель
        BookTableModel model = new BookTableModel();
        table = new JTable(model);
        model.addTableModelListener(table);
        scroll = new JScrollPane(table);

        //Размещение элементов
        panel.add(labaddress);
        panel.add(labemail);
        panel.add(labjob);
        panel.add(labname);
        panel.add(labphone);
        panel.add(labsurname);
        panel.add(tfaddress);
        panel.add(tfname);
        panel.add(tfemail);
        panel.add(tfjob);
        panel.add(tfphone);
        panel.add(tfsurname);
        panel.add(clearButton);

        add(panel);
        panel.add(deleteButton);
        panel.add(addButton);
        add(scroll);


        //Указание размеров и местоположения
        panel.setBounds(10,440,580,220);
        panel.setLayout(null);
        tfname.setBounds(120,30,130,30);
        tfsurname.setBounds(120,70,130,30);
        tfaddress.setBounds(120,110,130,30);
        tfphone.setBounds(430,30,130,30);
        tfemail.setBounds(430,70,130,30);
        tfjob.setBounds(430,110,130,30);

        labname.setBounds(20,30,90,30);
        labsurname.setBounds(20,70,90,30);
        labaddress.setBounds(20,110,90,30);
        labphone.setBounds(330, 30, 90, 30);
        labemail.setBounds(330,70,90,30);
        labjob.setBounds(330,110,90,30);

        clearButton.setBounds(430,170,130,30);
        deleteButton.setBounds(180,170,130,30);
        addButton.setBounds(20,170,130,30);
        scroll.setBounds(10,10,580,420);

        panel.setBorder(new TitledBorder("Добавление / удаление элементов"));

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfjob.setText("");
                tfemail.setText("");
                tfaddress.setText("");
                tfname.setText("");
                tfsurname.setText("");
                tfphone.setText("");
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow()>=0 && table.getSelectedRow()<book.getList().size()){
                    eventBus.dispatchEvent(new DeleteRecordEvent(book.getList().get(table.getSelectedRow()).getId()));
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventBus.dispatchEvent(new AddRecordEvent(new Record(tfname.getText(),tfsurname.getText(),tfaddress.getText(),tfemail.getText(),tfphone.getText(),tfjob.getText())));
            }
        });
    }


    class BookTableModel extends AbstractTableModel{

        private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
        String [] names = new String[] {"Имя","Фамилия","Адрес","Email","Телефон","Работа"};


        @Override
        public void addTableModelListener(TableModelListener l) {
            listeners.add(l);
        }

        @Override
        public void removeTableModelListener(TableModelListener l) {
            listeners.remove(l);
        }
        @Override
        public int getRowCount() {
            return book.getList().size();
        }

        @Override
        public int getColumnCount() {
            return Record.class.getDeclaredFields().length-1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            switch(columnIndex){
                case 0:
                    return book.getList().get(rowIndex).getName();
                case 1:
                    return book.getList().get(rowIndex).getSurname();
                case 2:
                    return book.getList().get(rowIndex).getAddress();
                case 3:
                    return book.getList().get(rowIndex).getEmail();
                case 4:
                    return book.getList().get(rowIndex).getPhoneNumber();
                case 5:
                    return book.getList().get(rowIndex).getJob();
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public String getColumnName(int column) {
            return names[column];
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if(book.getList() != null){
                switch (columnIndex) {
                    case 0:
                        book.getList().get(rowIndex).setName((String)aValue);
                        break;
                    case 1:
                        book.getList().get(rowIndex).setSurname((String) aValue);
                        break;
                    case 2:
                        book.getList().get(rowIndex).setAddress((String) aValue);
                        break;
                    case 3:
                        book.getList().get(rowIndex).setEmail((String) aValue);
                        break;
                    case 4:
                        book.getList().get(rowIndex).setPhoneNumber((String) aValue);
                        break;
                    case 5:
                        book.getList().get(rowIndex).setJob((String) aValue);
                        break;
                }
            }
        }

    }


}
