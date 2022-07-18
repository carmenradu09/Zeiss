package com.example.websocket.client.endpoint.client;

import org.jetbrains.annotations.NotNull;
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
public class WSClient {
    private static final Logger log = LoggerFactory.getLogger(WSClient.class);

    @OnOpen
    public void onOpen(Session session) {
        log.info("Connected to endpoint: {}", session.getBasicRemote());
    }

    @OnMessage
    public void processMessage(String message) {
        log.info("==========> Received message in client: {}", message);

        File eventsFromWSFile = prepareWriteToFile();

        log.info("========> Write message  {} from socket to file: {}", message, eventsFromWSFile);

        writeMessagesFromWSToFile(message, eventsFromWSFile);
    }

    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }

    @NotNull
    private File prepareWriteToFile() {
        File eventsFromWSFile = new File("EventsFromWS.txt");
        try {
            createFileIfNotPresent(eventsFromWSFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return eventsFromWSFile;
    }

    private void createFileIfNotPresent(File eventsFromWSFile) throws IOException {
        if (!eventsFromWSFile.exists()) {
            log.info("=====> File {} doesn't exist, creating one", eventsFromWSFile);

            eventsFromWSFile.createNewFile();
        }
    }

    private void writeMessagesFromWSToFile(String message, File eventsFromWSFile) {
        try {
            FileWriter fileWriter = new FileWriter(eventsFromWSFile, true);
            BufferedWriter writerToFile = new BufferedWriter(fileWriter);

            writerToFile.append(message);
            writerToFile.append("\n");

            log.info("=====> Wrote {} in file", message);

            writerToFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

