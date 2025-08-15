package com.journalAppPostgres.journalAppPostgres.repository;

import com.journalAppPostgres.journalAppPostgres.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntryRepository extends JpaRepository<User, Long> {
  User findByUserName(String username);

  void deleteByUserName(String userName);
}