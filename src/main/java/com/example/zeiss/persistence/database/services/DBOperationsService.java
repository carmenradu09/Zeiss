package com.example.zeiss.persistence.database.services;

import com.example.zeiss.persistence.database.message.DBMessage;
import com.example.zeiss.persistence.database.repository.DBMessageRepository;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Builder
public class DBOperationsService {
    @Autowired
    private DBMessageRepository dbMessageRepository;

    private static final Logger log = LoggerFactory.getLogger(DBOperationsService.class);

    public String addNewMessage(String topic,
                                String ref,
                                String timestamp,
                                String status,
                                String machineId,
                                String payloadId,
                                String joinRef,
                                String event) {

        DBMessage wsToDBMessage = buildDBMessage(
                topic, ref, timestamp, status, machineId, payloadId, joinRef, event);

        log.info("========> Write WS message topic to database: {}", wsToDBMessage.getTopic());
        log.info("========> Write WS message timestamp to database: {}", wsToDBMessage.getTimestamp());

        dbMessageRepository.save(wsToDBMessage);

        return "The message from the web socket is saved";
    }

    public Iterable<DBMessage> getAllMessages() {
        return dbMessageRepository.findAll();
    }

    private DBMessage buildDBMessage(String topic,
                                     String ref,
                                     String timestamp,
                                     String status,
                                     String machineId,
                                     String payloadId,
                                     String joinRef,
                                     String event) {
        return DBMessage.builder()
                .topic(topic)
                .ref(ref)
                .timestamp(timestamp)
                .status(status)
                .machineId(machineId)
                .payloadId(payloadId)
                .joinRef(joinRef)
                .event(event)
                .build();
    }
}
