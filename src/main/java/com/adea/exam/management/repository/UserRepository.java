package com.adea.exam.management.repository;

import com.adea.exam.management.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    int countByUser(String user);

    int countByClient(int client);

    Optional<UserEntity> findByUser(String user);


}
