package Engine.Core.Input.Events;

public class Event {
    public int type;
    public int key;
    public Event(int type, int key) {
        this.type = type;
        this.key = key;
    }
}
