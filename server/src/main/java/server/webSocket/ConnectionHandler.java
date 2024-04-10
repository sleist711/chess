package server.webSocket;

import com.google.gson.Gson;
import org.apache.commons.collections4.MultiValuedMap;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.websocket.api.Session;

import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.map.MultiValueMap;
public class ConnectionHandler {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    //this can't be a hash map
    public final ConcurrentHashMap<ArrayList<Integer>, Connection> usersInGames = new ConcurrentHashMap<>();

    public void add(String visitorName, Session session, Integer gameID) {

        var connection = new Connection(visitorName, session);
        connections.put(visitorName, connection);

        //maps each connection to a gameID
        ArrayList<Integer> gameIDList = new ArrayList<Integer>();
        gameIDList.add(gameID);

        usersInGames.put(gameIDList, connection);
    }

    public void remove(String visitorName) {
        connections.remove(visitorName);
    }

    public void broadcast(String excludeVisitorName, ServerMessage notification, Integer gameID) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : usersInGames.entrySet()) {
            if (c.getValue().session.isOpen()) {
                ArrayList<Integer> gameIDList = c.getKey();
                Integer idToCheck = gameIDList.getFirst();
                if (Objects.equals(idToCheck, gameID)) {
                    if (!c.getValue().visitorName.equals(excludeVisitorName)) {
                        c.getValue().send(notification.toString());
                    }

                }
            } else {
                removeList.add(c.getValue());
            }
        }

        /*
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.visitorName.equals(excludeVisitorName)) {
                    c.send(notification.toString());
                }
            }

        else {
                removeList.add(c);
            }
        }
        */

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.visitorName);
        }
    }


    public void sendAll(ServerMessage notification, Integer gameID) throws IOException {
        String message = new Gson().toJson(notification);

        for (var c : usersInGames.entrySet()) {
            if (c.getValue().session.isOpen()) {
                ArrayList<Integer> gameIDList = c.getKey();
                Integer idToCheck = gameIDList.getFirst();
                if (Objects.equals(idToCheck, gameID)) {
                    c.getValue().send(notification.toString());
                }
            }

        /*
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                c.send(message);

            }
        }

         */
        }
    }
}