package Server;

import java.io.Serializable;

public class Message implements Serializable {
    private float x, y;

    public Message(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Message setSentMessage(String data) {
        data = data.trim();
        String dataArr[] = data.split("&$&@");
        float x = Float.parseFloat(dataArr[0]);
        float y = Float.parseFloat(dataArr[1]);
        return new Message(x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getMessageToSend() {
        return x + "&$&@" + y;
    }
}
