package com.regalite.userservice.repository;

import com.regalite.userservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM userservice.user where valid_flg = 1 and email_verified = 1 and account_name = ?1", nativeQuery = true)
    Optional<User> findUserByAccountName(String accountName);

    @Query(value = "SELECT * FROM userservice.user where valid_flg = 1 and email_verified = 1 and email = ?1", nativeQuery = true)
    Optional<User>findUserByEmail(String email);
    @Query(value = "SELECT * FROM userservice.user where valid_flg = 1 and email_verified = 1 and phone_number = ?1", nativeQuery = true)
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    @Query(value = "SELECT * FROM userservice.user where valid_flg = 1 and email_verified = 1 and id = ?1", nativeQuery = true)
    Optional<User> findUserById(Long id);
}
