package com.sygec.sygec.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import javax.persistence.QueryHint;

import com.sygec.sygec.model.Servicee;

public interface ServiceeRepository extends JpaRepository<Servicee, String> {

    
}