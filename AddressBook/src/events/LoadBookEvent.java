package events;


public class LoadBookEvent implements Event {
    public static final String EVENT_TYPE = "LoadBookEvent";

    public String getType() {
        return EVENT_TYPE;
    }
}
