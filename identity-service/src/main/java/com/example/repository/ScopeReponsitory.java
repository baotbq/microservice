package com.example.repository;

import com.example.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScopeReponsitory extends JpaRepository<Scope, Long> {
}
