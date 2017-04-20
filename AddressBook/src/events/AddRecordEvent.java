package events;
import model.Record;

public class AddRecordEvent implements Event {
    public static final String EVENT_TYPE = "AddEventType";

    private String name;
    private String surname;
    private String address;
    private String email;
    private String phoneNumber;
    private String job;
    private int id;



    public AddRecordEvent(Record record){
        this.name = record.getName();
        this.surname = record.getSurname();
        this.address = record.getAddress();
        this.email = record.getEmail();
        this.phoneNumber = record.getPhoneNumber();
        this.job = record.getJob();
        this.id = record.getId();    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return EVENT_TYPE;
    }
}
