package com.example.restservice;

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

    @GetMapping("/greeting")
    public List<Greeting> greeting() throws IOException {

        List<Greeting> messages = new ArrayList<>();
        File fileToRead = new File(
                "/Users/carmenradu/IdeaProjects/github/Zeiss/EventsFromWS.txt");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToRead));

        String stringFromFile;
        Greeting.GreetingBuilder messageFromWebSocket = null;

        while ((stringFromFile = bufferedReader.readLine()) != null) {
            messageFromWebSocket = Greeting.builder().message(stringFromFile);

            messages.add(messageFromWebSocket.build());
        }

        return messages;
    }
}
