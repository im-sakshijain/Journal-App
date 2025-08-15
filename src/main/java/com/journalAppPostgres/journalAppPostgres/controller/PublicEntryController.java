package com.journalAppPostgres.journalAppPostgres.controller;

import com.journalAppPostgres.journalAppPostgres.entity.User;
import com.journalAppPostgres.journalAppPostgres.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/public")
public class PublicEntryController {

  @Autowired
  private UserEntryService userEntryService;

  @PostMapping("create-user")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    try {
      User savedUser = userEntryService.saveNewUser(user);
      return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
