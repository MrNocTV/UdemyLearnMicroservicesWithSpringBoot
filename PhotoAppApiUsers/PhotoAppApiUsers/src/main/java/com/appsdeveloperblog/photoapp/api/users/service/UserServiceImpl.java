package com.appsdeveloperblog.photoapp.api.users.service;

import com.appsdeveloperblog.photoapp.api.users.repository.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.repository.UserRepository;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.shared.UsernameNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private Environment environment;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserServiceImpl(final UserRepository userRepository, final Environment environment, final BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.environment = environment;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public UserDto createUser(UserDto userDetails) {
    userDetails.setUserId(UUID.randomUUID().toString());
    userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
    userRepository.save(userEntity);

    UserDto returnedValue = modelMapper.map(userEntity, UserDto.class);
    return returnedValue;
  }

  @Override
  public UserDto getUserDetailsByEmail(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);
    if (userEntity == null) throw new UsernameNotFoundException(email);

    return new ModelMapper().map(userEntity, UserDto.class);
  }

  @Override
  public UserDto getUserByUserId(String userId) {
    return null;
  }

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(userName);
    if (userEntity == null) throw new UsernameNotFoundException(userName);
    return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true,
            true, true, new ArrayList<>());
  }
}
