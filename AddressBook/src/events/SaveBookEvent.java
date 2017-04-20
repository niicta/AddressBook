package events;


public class SaveBookEvent implements Event {
    public static final String EVENT_TYPE = "SaveBookEvent";

    public String getType() {
        return EVENT_TYPE;
    }
}
