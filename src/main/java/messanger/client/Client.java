package messanger.client;

import java.io.*;
import java.net.Socket;

public class Client {

    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static MessageHistory messageHistory = new MessageHistory("message_history.txt");

    public static void main(String[] args) throws IOException {
        printLastHistoryMessages(messageHistory);
        try {
            try (Socket clientSocket = new Socket("localhost", 10001)) {
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                new Thread(() -> {
                    while (true) {
                        try {
                            String serverMessage = in.readLine();
                            System.out.println(serverMessage);
                            messageHistory.write(serverMessage);
                        } catch (IOException e) {
                            throw new RuntimeException("Server connection ERROR", e);
                        }
                    }
                }).start();
                while (true) {
                    String messageToServer = reader.readLine() + "\n";
                    out.write(messageToServer);
                    out.flush();
                    messageHistory.write(messageToServer);
                }
            } finally {
                System.out.println("Клиент был закрыт...");
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static void printLastHistoryMessages(MessageHistory messageHistory) {
        messageHistory.loadLast(100).forEach(System.out::println);
    }
}
