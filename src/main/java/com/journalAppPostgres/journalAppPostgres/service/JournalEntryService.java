package com.journalAppPostgres.journalAppPostgres.service;

import com.journalAppPostgres.journalAppPostgres.entity.Journal;
import com.journalAppPostgres.journalAppPostgres.entity.User;
import com.journalAppPostgres.journalAppPostgres.repository.JournalEntryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JournalEntryService {

  @Autowired
  private JournalEntryRepository journalEntryRepository;

  @Autowired
  private UserEntryService userService;


  @Transactional
  public Journal saveEntry(Journal journal, String userName) {
    User user = userService.findByUserName(userName);
    journal.setDate(LocalDateTime.now());
    journal.setUser(user); // set owning side
    return journalEntryRepository.save(journal); // JPA automatically updates the user_journals relationship
  }

  @Transactional
  public Journal saveEntry(Journal journal) {
    journal.setDate(LocalDateTime.now());
    return journalEntryRepository.save(journal);
  }


  public List<Journal> getAll() {
    return journalEntryRepository.findAll();
  }

  public Optional<Journal> getById(Long id) {
    return journalEntryRepository.findById(id);
  }

  @Transactional
  public void deleteById(Long id, String userName) {
    User user  = userService.findByUserName(userName);
    boolean remove = user.getJournals().removeIf(x -> x.getId().equals(id));
    if(remove){
      userService.saveEntry(user);
      journalEntryRepository.deleteById(id);
    }
//    Optional<Journal> journalOpt = journalEntryRepository.findByUserName(id);
//    if (journalOpt.isEmpty()) {
//      throw new EntityNotFoundException("Journal with id " + id + " not found");
//    }
//    // optionally check if journal belongs to userName here for extra safety
//
//    journalEntryRepository.deleteById(id);
//    // orphanRemoval will auto-remove from user's list if mapped properly
  }

  public Journal updateJournal(Long id, String userName, Journal newJournal) {
    User user = userService.findByUserName(userName);

    boolean ownsJournal = user.getJournals()
      .stream()
      .anyMatch(j -> j.getId().equals(id));

    if (!ownsJournal) {
      return null; // Journal not found or not owned by user
    }

    Journal existing = journalEntryRepository.findById(id)
      .orElseThrow();

    if (newJournal.getTitle() != null && !newJournal.getTitle().isEmpty()) {
      existing.setTitle(newJournal.getTitle());
    }
    if (newJournal.getContent() != null && !newJournal.getContent().isEmpty()) {
      existing.setContent(newJournal.getContent());
    }

    return journalEntryRepository.save(existing);
  }


  public Optional<Journal> findById(Long id) {
    return journalEntryRepository.findById(id);
  }
}
