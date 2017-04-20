package events;

import java.util.*;

public class EventBus {

    Map<String, List<Listener>> map = new HashMap<String, List<Listener>>();

    public void addEventListener(String eventType, Listener listener){
        List<Listener> list = map.get(eventType);
        if(list == null ){
           map.put(eventType, list = new ArrayList<Listener>());
        }
        list.add(listener);
    }


    public void dispatchEvent(Event event){
        String type = event.getType();
        List<Listener> listeners = map.get(type);
        if(listeners == null) return;
        for(Listener x : listeners){
            x.perform(event);
        }
    }
}
