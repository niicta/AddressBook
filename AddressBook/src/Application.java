import controller.AddressBookController;
import events.EventBus;
import events.LoadBookEvent;
import model.AddressBook;
import model.Record;
import view.MainView;

public class Application {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        AddressBook book = new AddressBook(eventBus);

        AddressBookController controller = new AddressBookController(book, eventBus);
        eventBus.dispatchEvent(new LoadBookEvent());
        MainView mainView = new MainView(book,eventBus);

    }
}
