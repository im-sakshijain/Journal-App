package com.journalAppPostgres.journalAppPostgres.controller;

import com.journalAppPostgres.journalAppPostgres.entity.User;
import com.journalAppPostgres.journalAppPostgres.repository.UserEntryRepository;
import com.journalAppPostgres.journalAppPostgres.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEntryController {

  @Autowired
  private UserEntryService userEntryService;

  @Autowired
  private UserEntryRepository userEntryRepository;

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    try {
      List<User> users = userEntryService.getAll();
      return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("id/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    Optional<User> user = userEntryService.getById(id);
    return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteUserById() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    userEntryService.deleteByUserName(authentication.getName());
      return new ResponseEntity<>(HttpStatus.OK);

  }

  @PutMapping
  public ResponseEntity<Void> updateUser(@RequestBody User user) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();
      User userInDb = userEntryService.findByUserName(userName);
      userInDb.setUserName(user.getUserName());
//      userInDb.setPassword(user.getPassword());
      userInDb.setRoles(user.getRoles());
      userEntryService.saveEntry(userInDb);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }