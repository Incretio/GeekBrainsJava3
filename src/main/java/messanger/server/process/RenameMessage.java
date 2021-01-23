package messanger.server.process;

import messanger.server.DbConnection;
import messanger.server.SocketConnection;

import java.util.List;

public class RenameMessage extends Message {
    
    public RenameMessage(SocketConnection owner, String content) {
        super(owner, content);
    }

    @Override
    public void process(List<SocketConnection> socketConnectionList, DbConnection dbConnection) {
        if (getContent().isEmpty() || getContent().contains(" ")) {
            getOwner().writeMessage("Некорретный формат запроса. [\\rename new_login]");
        } else {
            getOwner().setName(getContent());
            getOwner().writeMessage("Вы изменили свой ник на " + getContent());
        }
    }
    
}
