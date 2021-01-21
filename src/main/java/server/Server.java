package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Server {
    public static void main(String[] args) throws SQLException {
        Server server = new Server();

        server.setDbConnection(new DbConnection("jdbc:sqlite:db/messenger.db"));
        server.dbConnection.connect();

        server.startListenerForClients();
        System.out.println("Запущены слушатели клиентов.");
        while (true) {
            try {
                server.processClients();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private DbConnection dbConnection;
    private volatile List<SocketConnection> socketConnectionList = Collections.synchronizedList(new ArrayList<>());

    private void startListenerForClients() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(10001)) {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    SocketConnection socketConnection = new SocketConnection(clientSocket);
                    socketConnection.prepare();
                    socketConnectionList.add(socketConnection);
                    System.out.println("Подключен клиент");
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    private void processClients() {
        for (SocketConnection socketConnection : socketConnectionList) {
            try {
                Optional<Message> message = socketConnection.readMessage();
                message.ifPresent(this::processMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processMessage(Message message) {
        if (message.getMessageType() == MessageType.TEXT) {
            processTextMessage(message);
        }
        if (message.getMessageType() == MessageType.AUTH) {
            processAuthMessage(message);
        }
    }

    private void processTextMessage(Message message) {
        for (SocketConnection socketConnection : takeActiveConnections()) {
            System.out.println(
                String.format("Клиенту %s отправлено сообщение: %s", socketConnection.getName(), message.getContent()));
            socketConnection.writeMessage(new TextMessage().format(message));
        }
    }

    private void processAuthMessage(Message message) {
        String[] splitted = message.getContent().split(" ");
        if (splitted.length == 2) {
            String login = splitted[0];
            String password = splitted[1];
            if (dbConnection.checkUser(login, password)) {
                message.getOwner().setActive(true);
                message.getOwner().setName(login);
                message.getOwner().writeMessage("Вы авторизовались как " + login);
            } else {
                message.getOwner().writeMessage("Ошибка авторизации");
            }
        } else {
            message.getOwner().writeMessage("Некорретный формат запроса. [\\auth login password]");
        }

    }

    private List<SocketConnection> takeActiveConnections() {
        return socketConnectionList.stream().filter(SocketConnection::isActive).collect(Collectors.toList());
    }

    public DbConnection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
}
