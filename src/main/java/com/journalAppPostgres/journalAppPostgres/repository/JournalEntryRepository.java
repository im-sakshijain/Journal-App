package com.journalAppPostgres.journalAppPostgres.repository;

import com.journalAppPostgres.journalAppPostgres.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryRepository extends JpaRepository<Journal, Long> {
}

