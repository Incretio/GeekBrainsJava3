package messanger.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public class MessageHistory {

    private final String historyFilePath;

    public MessageHistory(String historyFilePath) {
        this.historyFilePath = historyFilePath;
    }

    public List<String> loadLast(int count) {
        try {
            List<String> data = Files.readAllLines(Paths.get(historyFilePath), StandardCharsets.UTF_8);
            return data.subList(Math.max(0, data.size() - count), data.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void write(String message) {
        try {
            Files.write(Paths.get(historyFilePath), message.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}