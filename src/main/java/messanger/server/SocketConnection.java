package messanger.server;

import messanger.server.process.Message;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class SocketConnection {

    private final Socket socket;

    private boolean active = false;
    private String name = "anonymous";
    private BufferedReader reader;
    private BufferedWriter writer;

    public SocketConnection(Socket socket) {
        this.socket = socket;
    }

    public void prepare() throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public Optional<Message> readMessage() throws IOException {
        if (!reader.ready()) {
            return Optional.empty();
        }
        String data = reader.readLine();
        return Optional.of(getMessage(data));
    }

    public void writeMessage(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            active = false;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private Message getMessage(String data) {
        return Message.constructMessage(this, data);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
