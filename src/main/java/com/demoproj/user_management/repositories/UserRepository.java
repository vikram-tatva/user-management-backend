package com.demoproj.user_management.repositories;

import com.demoproj.user_management.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);

    @Query("""
            SELECT u FROM User u WHERE 
             LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR
             LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR
             LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR
             LOWER(u.mobile) LIKE LOWER(CONCAT('%', :keyword ,'%')) OR
             LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword ,'%'))
                         """)
    List<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);
}
