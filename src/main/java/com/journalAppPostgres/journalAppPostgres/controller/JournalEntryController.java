package com.journalAppPostgres.journalAppPostgres.controller;


import com.journalAppPostgres.journalAppPostgres.entity.Journal;
import com.journalAppPostgres.journalAppPostgres.entity.User;
import com.journalAppPostgres.journalAppPostgres.service.JournalEntryService;
import com.journalAppPostgres.journalAppPostgres.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

  @Autowired
  private JournalEntryService journalEntryService;

  @Autowired
  private UserEntryService userEntryService;

  @GetMapping
  public ResponseEntity<List<Journal>> getAllJournalEntriesOfUsers(){
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();
      User user = userEntryService.findByUserName(userName);
      List<Journal> all = user.getJournals();
      return new ResponseEntity<>(all, HttpStatus.OK);
    } catch( Exception e){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping
  public ResponseEntity<Journal> createJournal(@RequestBody Journal journal){
    try{
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();
      journalEntryService.saveEntry(journal, userName);
      return new ResponseEntity<>(journal, HttpStatus.CREATED);
    }
    catch(Exception e){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("id/{id}")
  public ResponseEntity<Journal> getJournalById(@PathVariable Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    User user = userEntryService.findByUserName(userName);
    List<Journal> collect = user.getJournals().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
    if(!collect.isEmpty()){
      Optional<Journal> journal = journalEntryService.getById(id);
      if(journal.isPresent()){
        return new ResponseEntity<>(journal.get(),HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("id/{id}")
  public ResponseEntity<Void> deleteJournalById(@PathVariable Long id) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();
      journalEntryService.deleteById(id, userName);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("id/{id}")
  public ResponseEntity<Journal> updateJournal(@PathVariable Long id,
                                               @RequestBody Journal newJournal
                                             ) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    Journal journal = journalEntryService.updateJournal(id, userName, newJournal);
    if(journal!=null){
      return new ResponseEntity<>(journal, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
