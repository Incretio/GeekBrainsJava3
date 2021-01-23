package messanger.server.process;

import messanger.server.DbConnection;
import messanger.server.SocketConnection;

import java.util.List;

public class NonMessage extends Message {

    public NonMessage(SocketConnection owner, String content) {
        super(owner, content);
    }

    @Override
    public void process(List<SocketConnection> socketConnectionList, DbConnection dbConnection) {
        // noop
    }

}
