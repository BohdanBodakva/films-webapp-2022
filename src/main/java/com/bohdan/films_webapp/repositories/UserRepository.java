package com.bohdan.films_webapp.repositories;

import com.bohdan.films_webapp.DAO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
