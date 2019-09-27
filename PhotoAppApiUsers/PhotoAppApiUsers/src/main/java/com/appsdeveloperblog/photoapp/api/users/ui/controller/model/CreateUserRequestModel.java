package com.appsdeveloperblog.photoapp.api.users.ui.controller.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
  @NotNull(message = "First name cannot be null")
  @Size(min = 2, message = "First name must be less than 2 characters")
  private String firstName;
  private String lastName;
  private String email;
  private String password;

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
