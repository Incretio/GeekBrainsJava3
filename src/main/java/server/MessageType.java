package server;

public enum MessageType {
    NON("\\non"),
    TEXT("\\message");

    private final String command;

    MessageType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static MessageType detectMessageType(String data) {
        if (data == null || data.isEmpty()) {
            return NON;
        }
        for (MessageType messageType : values()) {
            if (data.startsWith(messageType.getCommand())) {
                return messageType;
            }
        }
        return NON;
    }
}
