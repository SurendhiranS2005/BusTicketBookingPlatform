package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.username = :username")
    List<User> findByUsername(@Param("username") String username);

    // @Transactional
    @Modifying
    @Query("UPDATE User u SET u.username = :username WHERE u.id = :id")
    int updateUsername(@Param("id") Long id, @Param("username") String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :id")
    int deleteUserById(@Param("id") Long id);
}
