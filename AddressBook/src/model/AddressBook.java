package model;

import events.EventBus;

import java.util.ArrayList;




public class AddressBook {
    private ArrayList<Record> list = new ArrayList<Record>();
    private EventBus eventBus;

    public AddressBook(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public ArrayList<Record> getList() {
        ArrayList<Record> copy = new ArrayList<Record>();
        copy.addAll(list);
        return copy;
    }

    public void setList(ArrayList<Record> list) {
        this.list = list;

        /*new ArrayList<Record>();
        this.list.addAll(list);*/
    }
}
