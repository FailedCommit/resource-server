package com.maat.authserver.security.services;

import com.maat.authserver.entities.User;
import com.maat.authserver.repositories.UserRepository;
import com.maat.authserver.security.model.UserDetailsSecurityWrapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public JpaUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findUserByUsername(username);
    return user
            .map(UserDetailsSecurityWrapper::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
  }
}
