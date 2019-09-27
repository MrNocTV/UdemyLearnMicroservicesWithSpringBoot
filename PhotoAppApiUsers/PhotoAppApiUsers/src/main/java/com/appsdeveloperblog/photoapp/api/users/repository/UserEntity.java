package com.appsdeveloperblog.photoapp.api.users.repository;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

  private static final long serialVersionUID = -5605630022832932340L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @Column(nullable=false, length=50)
  private String firstName;

  @Column(nullable=false, length=50)
  private String lastName;

  @Column(nullable=false, length=120, unique=true)
  private String email;

  @Column(nullable=false, unique=true)
  private String userId;

  @Column(nullable=false, unique=true)
  private String encryptedPassword;

  public long getId() {
    return id;
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

  public String getUserId() {
    return userId;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }
}
