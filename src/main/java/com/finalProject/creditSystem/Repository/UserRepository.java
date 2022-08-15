package com.finalProject.creditSystem.Repository;

import com.finalProject.creditSystem.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    User findByUsername(String username);


    @Transactional
    void deleteByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
