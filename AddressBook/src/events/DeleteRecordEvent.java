package events;

public class DeleteRecordEvent implements Event {
    public static final String EVENT_TYPE = "DeleteRecordEvent";

    public int ID;

    public DeleteRecordEvent(int ID){
        this.ID = ID;
    }
    @Override
    public String getType() {
        return EVENT_TYPE;
    }
}
