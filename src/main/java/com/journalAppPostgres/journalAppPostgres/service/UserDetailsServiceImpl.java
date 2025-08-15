package com.journalAppPostgres.journalAppPostgres.service;


import com.journalAppPostgres.journalAppPostgres.entity.User;
import com.journalAppPostgres.journalAppPostgres.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserEntryRepository userEntryRepository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
    User byUserName = userEntryRepository.findByUserName(username);
    if(byUserName!=null){
      return org.springframework.security.core.userdetails.User.builder()
        .username(byUserName.getUserName()).password(byUserName.getPassword()).roles(byUserName.getRoles().toArray(new String[0]))
        .build();
    }

    throw new UsernameNotFoundException("User not found with username: " + username);


  }

}
