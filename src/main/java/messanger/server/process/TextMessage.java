package messanger.server.process;

import messanger.server.DbConnection;
import messanger.server.SocketConnection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TextMessage extends Message {

    public TextMessage(SocketConnection owner, String content) {
        super(owner, content);
    }

    public String format(Message message) {
        return String.format("[%s] %s: %s", takeTimeStamp(), message.getOwnerName(), message.getContent());
    }

    private String takeTimeStamp() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
    }

    @Override
    public void process(List<SocketConnection> socketConnectionList, DbConnection dbConnection) {
        for (SocketConnection socketConnection : socketConnectionList) {
            System.out.println(
                String.format("Клиенту %s отправлено сообщение: %s", socketConnection.getName(), getContent()));
            socketConnection.writeMessage(format(this));
        }
    }
}
