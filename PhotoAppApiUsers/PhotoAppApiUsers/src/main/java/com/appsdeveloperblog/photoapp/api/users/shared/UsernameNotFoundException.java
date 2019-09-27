package com.appsdeveloperblog.photoapp.api.users.shared;

public class UsernameNotFoundException extends RuntimeException {
  public UsernameNotFoundException(String email) {
    super(email);
  }
}
