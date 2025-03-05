package com.sygec.sygec.repository;


import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.sygec.sygec.model.StoredFile;

import java.util.Optional;
@Repository
public interface StorageRepository extends JpaRepository<StoredFile, String> {
    Optional<StoredFile> findByName(String fileName);
}
