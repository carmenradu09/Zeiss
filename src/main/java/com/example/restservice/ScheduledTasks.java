package com.example.restservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    final CountDownLatch messageLatch = new CountDownLatch(1);

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        try {
            log.info("======> The time is now {}", dateFormat.format(new Date()));
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            String uri = "ws://machinestream.herokuapp.com/ws";

            System.out.println("===============> Connecting to " + uri);

            container.connectToServer(MyClientEndpoint.class, URI.create(uri));

            log.info("==================> Connected to: {}", uri);


            messageLatch.await(60, TimeUnit.SECONDS);
        } catch (DeploymentException | InterruptedException | IOException ex) {
        }

    }
}
