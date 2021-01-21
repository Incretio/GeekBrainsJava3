package client;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) throws IOException {
        try {
            try {
                clientSocket = new Socket("localhost", 10001);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                new Thread(() -> {
                    while (true) {
                        try {
                            String serverWord = in.readLine();
                            System.out.println(serverWord);
                        } catch (IOException e) {
                            throw new RuntimeException("Server connection ERROR", e);
                        }
                    }
                }).start();
                while (true) {
                    String word = reader.readLine() + "\n";
                    out.write(word);
                    out.flush();
                }
            } finally {
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
