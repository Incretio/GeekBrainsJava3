package messanger.server.process;

import messanger.server.DbConnection;
import messanger.server.SocketConnection;

import java.util.List;

public class AuthMessage extends Message {

    public AuthMessage(SocketConnection owner, String content) {
        super(owner, content);
    }

    @Override
    public void process(List<SocketConnection> socketConnectionList, DbConnection dbConnection) {
        String[] splitted = getContent().split(" ");
        if (splitted.length == 2) {
            String login = splitted[0];
            String password = splitted[1];
            if (dbConnection.checkUser(login, password)) {
                getOwner().setActive(true);
                getOwner().setName(login);
                getOwner().writeMessage("Вы авторизовались как " + login);
            } else {
                getOwner().writeMessage("Ошибка авторизации");
            }
        } else {
            getOwner().writeMessage("Некорретный формат запроса. [\\auth login password]");
        }
    }

}
