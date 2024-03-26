package com.paul.demo.repositories;

import com.paul.demo.entities.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {


//    List<Message> findByCreationDateBeforeOrCreationDateAndIdBeforeOrderByCreationDateDescIdDesc(Date startDate, Date startDate2, String minIdValue, Pageable pageable);


    List<Message> findTopNByOrderByCreationDateDescIdDesc(Pageable pageable);

    @Query(value = "{ '$or': [ " +
            "  { 'creationDate': { '$lt': ?0 } }, " +
            "  { '$and': [ " +
            "      { 'creationDate': ?0 }, " +
            "      { '_id': { '$lt': ?1 } } " +
            "    ] " +
            "  } " +
            "] }",
            sort = "{ 'creationDate': -1, '_id': -1 }")
    List<Message> getLastMessagesFrom(
            Date startDate,
            String minIdValue,
            Pageable pageable
    );


}
