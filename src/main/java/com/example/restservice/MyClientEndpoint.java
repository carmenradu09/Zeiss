package com.example.restservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@ClientEndpoint
public class MyClientEndpoint {
    private static final Logger log = LoggerFactory.getLogger(MyClientEndpoint.class);

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
    }

    @OnMessage
    public void processMessage(String message) {
        System.out.println("==========> Received message in client: " + message);

        File eventsFromWS = new File("EventsFromWS.txt");
        try {
            if (!eventsFromWS.exists()) {
                System.out.println("=====> File doesn't exist, creating one");
                eventsFromWS.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("========> Write message  {} from socket to file: {}", message, eventsFromWS);

        try {
            FileWriter fileWriter = new FileWriter(eventsFromWS, true);
            BufferedWriter writerToFile = new BufferedWriter(fileWriter);

            writerToFile.append(message);
            writerToFile.append("\n");

            log.info("=====> Wrote {} in file", message);

            writerToFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }
}

