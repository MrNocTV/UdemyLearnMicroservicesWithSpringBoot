package com.appsdeveloperblog.photoapp.api.users.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
  UserEntity findByEmail(final String email);
  UserEntity findByUserId(final String userId);
}
