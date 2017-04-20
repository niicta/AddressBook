package controller;
import model.AddressBook;
import model.Record;
import events.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookController {

    private static final String filename = "bookdump.txt";
    private AddressBook book;
    private EventBus eventBus;

    public AddressBookController(final AddressBook book, EventBus eventBus) {
        this.book = book;
        this.eventBus = eventBus;

        eventBus.addEventListener(LoadBookEvent.EVENT_TYPE, new Listener() {
            @Override
            public void perform(Event a) {
                loadBook(book);
            }
        });

        eventBus.addEventListener(SaveBookEvent.EVENT_TYPE, new Listener() {
            @Override
            public void perform(Event a) {
                saveBook(book);
            }
        });


        eventBus.addEventListener(AddRecordEvent.EVENT_TYPE, new Listener() {
            @Override
            public void perform(Event a) {
                addRecord(book, (AddRecordEvent) a);
            }
        });

        eventBus.addEventListener(DeleteRecordEvent.EVENT_TYPE, new Listener() {
            @Override
            public void perform(Event a) {
                deleteRecord(book, (DeleteRecordEvent) a);
            }
        });

    }

    public void loadBook(AddressBook book){
        try{
            if(!new File(filename).exists()) return;
            FileInputStream fileInputStream = new FileInputStream(filename);

            if(fileInputStream.available() == 0) return;

            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            ArrayList list = (ArrayList) inputStream.readObject();
            book.setList(list);
            inputStream.close();
        }catch(IOException exep){
            exep.printStackTrace();
        }catch(ClassNotFoundException exep){
            exep.printStackTrace();
        }
    }

    public void saveBook(AddressBook book){
        try {
            File filedump = new File(filename);
            if(!new File(filename).exists()) {
                filedump.createNewFile();
            }
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filedump));
            outputStream.writeObject(book.getList());
            outputStream.close();
        }catch(IOException exep){
            exep.printStackTrace();
        }
    }

    public void deleteRecord(AddressBook book, DeleteRecordEvent deleteRecordEvent){
        ArrayList<Record> list = new ArrayList<Record>();
        ArrayList<Record> current = book.getList();
        for (Record record : current) {
            if(record.getId() != deleteRecordEvent.ID){
                list.add(record);
            }
        }
        book.setList(list);
        eventBus.dispatchEvent(new BookChangedEvent());
    }


    public void addRecord(AddressBook book, AddRecordEvent addRecordEvent){
        List<Record> oldList = book.getList();
        ArrayList<Record> newList = new ArrayList<Record>();
        newList.addAll(oldList);
        newList.add(new Record(addRecordEvent.getName(),addRecordEvent.getSurname(),addRecordEvent.getAddress(),addRecordEvent.getEmail(),addRecordEvent.getPhoneNumber(),addRecordEvent.getJob() ));
        book.setList(newList);
        eventBus.dispatchEvent(new BookChangedEvent());
    }
}
