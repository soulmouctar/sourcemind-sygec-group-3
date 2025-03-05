package com.sygec.sygec.repository;

import com.sygec.sygec.model.SequenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SequenceRepository extends JpaRepository<SequenceEntity, String> {
}
