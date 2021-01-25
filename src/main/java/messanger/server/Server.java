package messanger.server;

import messanger.server.process.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws SQLException {
        Server server = new Server();

        server.setDbConnection(new DbConnection("jdbc:sqlite:db/messenger.db"));
        server.dbConnection.connect();

        server.startListenerForClients();
        logger.debug("Запущены слушатели клиентов.");
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
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try (ServerSocket serverSocket = new ServerSocket(10001)) {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    SocketConnection socketConnection = new SocketConnection(clientSocket);
                    socketConnection.prepare();
                    socketConnectionList.add(socketConnection);
                    logger.debug("Подключен клиент");
                }
            } catch (IOException e) {
                logger.error("Socket open ERROR", e);
            }
        });
    }

    private void processClients() {
        for (SocketConnection socketConnection : socketConnectionList) {
            try {
                Optional<Message> message = socketConnection.readMessage();
                message.ifPresent(messageValue -> messageValue.process(takeActiveConnections(), dbConnection));
            } catch (IOException e) {
                logger.error("Process message ERROR", e);
            }
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
