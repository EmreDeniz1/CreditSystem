package com.finalProject.creditSystem.Repository;

import com.finalProject.creditSystem.Entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findAllByUsername(String username);
}
