package server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextMessage {

    public String format(Message message) {
        return String.format("[%s] %s: %s", takeTimeStamp(), message.getOwnerName(), message.getContent());
    }

    private String takeTimeStamp() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
    }

}
