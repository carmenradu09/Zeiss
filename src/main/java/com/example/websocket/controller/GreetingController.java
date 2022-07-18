package com.example.websocket.controller;

import com.example.websocket.client.endpoint.component.ScheduledTasks;
import com.example.websocket.message.WSMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GreetingController {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @GetMapping("/messages")
    public List<WSMessage> greeting() throws IOException {
        List<WSMessage> messages = new ArrayList<>();

        File fileToRead = new File(
                "/Users/carmenradu/IdeaProjects/github/Zeiss/EventsFromWS.txt");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToRead));

        readWSMessagesFromFile(messages, bufferedReader);

        return messages;
    }

    private void readWSMessagesFromFile(List<WSMessage> messages, BufferedReader bufferedReader) throws IOException {
        WSMessage.WSMessageBuilder messageFromWebSocket;
        String stringFromFile;

        while ((stringFromFile = bufferedReader.readLine()) != null) {
            messageFromWebSocket = WSMessage.builder().message(stringFromFile);

            messages.add(messageFromWebSocket.build());
        }
    }
}
