package messanger.server.process;

import messanger.server.DbConnection;
import messanger.server.SocketConnection;

import java.util.List;

public abstract class Message {

    private final SocketConnection owner;
    private final String content;

    public Message(SocketConnection owner, String content) {
        this.owner = owner;
        this.content = content;
    }

    abstract public void process(List<SocketConnection> socketConnectionList, DbConnection dbConnection);

    public String getContent() {
        return content;
    }

    public String getOwnerName() {
        return owner.getName();
    }

    public SocketConnection getOwner() {
        return owner;
    }

    public static Message constructMessage(SocketConnection owner, String data) {
        MessageType messageType = MessageType.detectMessageType(data);
        String messageContent = data.substring(messageType.getCommand().length()).trim();
        switch (messageType) {
            case TEXT:
                return new TextMessage(owner, messageContent);
            case AUTH:
                return new AuthMessage(owner, messageContent);
            case RENAME:
                return new RenameMessage(owner, messageContent);
            default:
                return new NonMessage(owner, messageContent);
        }
    }

}
