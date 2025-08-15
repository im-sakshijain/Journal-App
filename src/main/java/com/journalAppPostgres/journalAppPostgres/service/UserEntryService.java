package com.journalAppPostgres.journalAppPostgres.service;

import com.journalAppPostgres.journalAppPostgres.entity.User;
import com.journalAppPostgres.journalAppPostgres.repository.UserEntryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j//jis tarah se lombol likhte woh getter setter ko inject krdeta compile time k dorun
//waise hi slf4 bhi inject krdega nstance ko log naam ka instance bnta ok
public class UserEntryService {
  @Autowired
  private UserEntryRepository userEntryRepository;


  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//iski jagah apan @Slf4j use kr skte hai ok

  //sl4j abstraction hai logging abstraction frmeweork iski help se underlying se bat krpyanege//logback impleent kr rha hai
  //simple logging fascade for java
  //har class ka apna alag instance hoga toh private accidental reassignment se bachne k liye
  //final ek hi instance chahte hai toh static
  private final static Logger logger = LoggerFactory.getLogger(UserEntryService.class);
  //info, error, warn by default rhte hai debug aur trace k liye customization krna pdega ok


  public User saveNewUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(Arrays.asList("User"));
    return userEntryRepository.save(user);
  }
  public User saveEntry(User user){
    return userEntryRepository.save(user);
  }
  @Transactional
  public void deleteByUserName(String userName) {
    userEntryRepository.deleteByUserName(userName);
  }

  public List<User> getAll() {
//    logger.info("USER ENTRY SERVICE LOGS!!! {}", e); {} aise krke apan argument pass krr skte ok?
    //
    log.error("USER ERROR LOGS");
    log.warn("USER WARN LOGS");
    log.debug("USER DEBUG LOGS!");
    log.trace("USER DEBUG LOGS!!");
    return userEntryRepository.findAll();
  }

  public void deleteUser(Long id) {
    userEntryRepository.deleteById(id);
  }

  public Optional<User> getById(Long id) {
    return userEntryRepository.findById(id);
  }

  public User updateUser(Long id, User user) {
    // Ensure we're updating the correct user
    user.setId(id);
    return userEntryRepository.save(user);
  }

  public User findByUserName(String userName) {
    return userEntryRepository.findByUserName(userName);
  }
}
