package com.example.phonenumbersapi.repository;

import com.example.phonenumbersapi.entity.NumberSuffix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberSuffixRepository extends JpaRepository<NumberSuffix, Long> {
}
