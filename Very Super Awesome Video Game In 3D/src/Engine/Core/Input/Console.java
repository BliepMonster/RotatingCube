package Engine.Core.Input;

import Engine.Core.Input.Events.*;

import static Engine.Extra.*;

public class Console {
    public final Input input = new Input();
    public void press(int key) {
        input.events.add(new Event(KEY_DOWN, key));
    }
}
