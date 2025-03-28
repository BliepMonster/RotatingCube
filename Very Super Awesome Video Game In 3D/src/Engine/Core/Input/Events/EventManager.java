package Engine.Core.Input.Events;

import java.util.ArrayList;

public class EventManager {
    private final ArrayList<Event> eventList = new ArrayList<>();
    public void add(Event e) {
        eventList.add(e);
    } public ArrayList<Event> get() {
        return eventList;
    } public void clear() {
        eventList.clear();
    }
}
