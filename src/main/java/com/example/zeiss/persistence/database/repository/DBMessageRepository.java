package com.example.zeiss.persistence.database.repository;

import com.example.zeiss.persistence.database.message.DBMessage;
import org.springframework.data.repository.CrudRepository;

public interface DBMessageRepository extends CrudRepository<DBMessage, Integer> {
}
