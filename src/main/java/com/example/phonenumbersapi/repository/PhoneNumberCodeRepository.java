package com.example.phonenumbersapi.repository;

import com.example.phonenumbersapi.entity.PhoneNumberCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberCodeRepository extends JpaRepository<PhoneNumberCode, Long> {
}
