package com.example.websocket.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WSMessage {
    private String message;
}
