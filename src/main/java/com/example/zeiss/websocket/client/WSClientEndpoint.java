package com.example.zeiss.websocket.client;

import com.example.zeiss.persistence.database.services.DBOperationsService;
import com.example.zeiss.websocket.database.mapper.WSJsonToDBMessageMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

@ClientEndpoint
public class WSClientEndpoint {
//    @Autowired
//    DBOperationsService dbOperationsService;

    private static final Logger log = LoggerFactory.getLogger(WSClientEndpoint.class);

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
    }

    @OnMessage
    public void processMessage(String message) throws IOException {
        log.info("==========> Received message in client: {} ", message);

        ObjectMapper objectMapper = new ObjectMapper();
        WSJsonToDBMessageMapper wsJsonToDBMessageMapper =
                objectMapper.readValue(message, WSJsonToDBMessageMapper.class);

        String saveMessageToDB = addNewMessageToDatabase(wsJsonToDBMessageMapper);

        log.info("========> Save message to database: {}", saveMessageToDB);

        addNewMessageToDatabase(wsJsonToDBMessageMapper);
    }

    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }

    private String addNewMessageToDatabase(WSJsonToDBMessageMapper wsJsonToDBMessageMapper) {
        DBOperationsService dbOperationsService = DBOperationsService.builder().build();

        return dbOperationsService.addNewMessage(
                wsJsonToDBMessageMapper.getTopic(),
                wsJsonToDBMessageMapper.getRef(),
                wsJsonToDBMessageMapper.getPayload().getTimestamp(),
                wsJsonToDBMessageMapper.getPayload().getStatus(),
                wsJsonToDBMessageMapper.getPayload().getMachine_id(),
                wsJsonToDBMessageMapper.getPayload().getId(),
                wsJsonToDBMessageMapper.getJoin_ref(),
                wsJsonToDBMessageMapper.getEvent());
    }
}
