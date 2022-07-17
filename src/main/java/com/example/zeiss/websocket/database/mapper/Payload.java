package com.example.zeiss.websocket.database.mapper;

import lombok.Data;

@Data
public class Payload {
    private String timestamp;
    private String status;
    private String machine_id;
    private String id;
}
