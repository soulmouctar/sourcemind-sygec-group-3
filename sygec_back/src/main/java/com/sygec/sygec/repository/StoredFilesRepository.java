package com.sygec.sygec.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sygec.sygec.model.StoredFile;

public interface StoredFilesRepository extends JpaRepository<StoredFile, String> 
{
	StoredFile findByUuid(String id);
}
