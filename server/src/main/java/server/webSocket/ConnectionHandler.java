package server.webSocket;

import com.google.gson.Gson;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.websocket.api.Session;

import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionHandler {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    //this can't be a hash map
    //still having issues with the map 
    public final ConcurrentHashMap<Connection, Integer> usersInGames = new ConcurrentHashMap<>();

    public void add(String visitorName, Session session, Integer gameID) {

        var connection = new Connection(visitorName, session);
        connections.put(visitorName, connection);

        //maps each connection to a gameID
        ArrayList<Integer> gameIDList = new ArrayList<Integer>();
        gameIDList.add(gameID);

        usersInGames.put(connection, gameID);
    }

    public void remove(String visitorName) {
        connections.remove(visitorName);
    }

    public void broadcast(String excludeVisitorName, ServerMessage notification, Integer gameID) throws IOException {


        var removeList = new ArrayList<Connection>();

        for (var c : usersInGames.entrySet()) {
            if (c.getKey().session.isOpen()) {
                if (Objects.equals(c.getValue(), gameID)) {
                    if (!c.getKey().visitorName.equals(excludeVisitorName)) {
                        c.getKey().send(notification.toString());
                    }

                }
            } else {
                removeList.add(c.getKey());
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
            if (c.getKey().session.isOpen()) {
                if (Objects.equals(c.getValue(), gameID)) {
                    c.getKey().send(notification.toString());
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
