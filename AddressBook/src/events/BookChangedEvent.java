package events;



public class BookChangedEvent implements Event{
    public static final String EVENT_TYPE = "BookChangedEvent";

    public String getType() {
        return EVENT_TYPE;
    }
}



