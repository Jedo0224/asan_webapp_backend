package com.asanhospital.server.service.DatabaseSequence;

import com.asanhospital.server.domain.DatabaseSequence;
import com.asanhospital.server.repository.DatabaseSequenceRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class DatabaseSequenceService {
    private final DatabaseSequenceRepository databaseSequenceRepository;

    public long generateSequence(String seqName) {
        DatabaseSequence counter = databaseSequenceRepository.findById(seqName).orElse(null);
        if (Objects.isNull(counter)) {
            counter = new DatabaseSequence();
            counter.setId(seqName);
            counter.setSeq(0L);
        }
        counter.setSeq(counter.getSeq() + 1);
        databaseSequenceRepository.save(counter);
        return counter.getSeq();
    }
}
