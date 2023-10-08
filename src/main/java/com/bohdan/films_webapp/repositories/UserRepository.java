package com.bohdan.films_webapp.repositories;

import com.bohdan.films_webapp.DAO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    @Query("select u from User u where u.status='BANNED'")
    List<User> findAllBannedUsers();
    @Query("select u from User u where u.status='DELETED'")
    List<User> findAllDeletedUsers();
    @Query("select u from User u where u.status='ACTIVE'")
    List<User> findAllActiveUsers();

}
