package com.example.zeiss.websocket.database.mapper;

import lombok.Data;

@Data
public class WSJsonToDBMessageMapper {
    private String topic;
    private String ref;
    private Payload payload;
    private String join_ref;
    private String event;
}
