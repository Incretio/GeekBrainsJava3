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
    }

    private void processTextMessage(Message message) {
        for (SocketConnection socketConnection : takeActiveConnections()) {
            System.out.println(
                String.format("Клиенту %s отправлено сообщение: %s", socketConnection.getName(), message.getContent()));
            socketConnection.writeMessage(new TextMessage().format(message));
        }
    }

    private List<SocketConnection> takeActiveConnections() {
        return socketConnectionList.stream().filter(SocketConnection::isActive).collect(Collectors.toList());
    }

}