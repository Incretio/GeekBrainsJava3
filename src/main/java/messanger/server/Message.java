package messanger.server;

public class Message {

    private final MessageType messageType;
    private final SocketConnection owner;
    private final String content;

    public Message(SocketConnection owner, MessageType messageType, String content) {
        this.owner = owner;
        this.messageType = messageType;
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getContent() {
        return content;
    }

    public String getOwnerName() {
        return owner.getName();
    }

    public SocketConnection getOwner() {
        return owner;
    }

    public boolean iAmOwner(SocketConnection socketConnection) {
        return owner == socketConnection;
    }

    public static Message constructMessage(SocketConnection owner, String data) {
        MessageType messageType = MessageType.detectMessageType(data);
        String messageContent = data.substring(messageType.getCommand().length()).trim();
        return new Message(owner, messageType, messageContent);
    }
}
