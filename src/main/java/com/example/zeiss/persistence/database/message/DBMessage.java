package com.example.zeiss.persistence.database.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
public class DBMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String topic;

    private String ref;
    private String timestamp;
    private String status;
    private String machineId;
    private String payloadId;

    private String joinRef;
    private String event;
}
