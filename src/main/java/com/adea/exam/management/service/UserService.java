package com.adea.exam.management.service;

import com.adea.exam.management.UsersDao;
import com.adea.exam.management.config.MessagesProperties;
import com.adea.exam.management.controller.beans.*;
import com.adea.exam.management.entity.UserEntity;
import com.adea.exam.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MessagesProperties messagesProperties;
    private final UsersDao usersDao;


    @Transactional(readOnly = true)
    public UserRecordResponse getUser(String id) {
        var response = new UserRecordResponse();
        var optional = userRepository.findById(id);
        if (optional.isPresent()) {
            var eo = optional.get();
            response.setUser(buildUserRecord(eo));
        } else {
            response.setErrorMessage(messagesProperties.getUserExist().formatted(id));
        }
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Response setNewUser(UserRecord userRecord) {
        var response = new Response();
        if (userRepository.countByUser(userRecord.login()) == 0) {
            if (userRepository.countByClient(userRecord.client().intValue()) == 0) {
                userRepository.save(buildUserEntity(userRecord));
            } else {
                response.setErrorMessage(messagesProperties.getClientExist().formatted(userRecord.client()));
            }
        } else {
            response.setErrorMessage(messagesProperties.getUserExist().formatted(userRecord.login()));
        }
        return response;
    }

    @Transactional(readOnly = true)
    public UsersResponse getUsers(UserFilterRequest userFilterRequest) {
        var users = new UsersResponse();
        var optional = usersDao.getUsers(userFilterRequest);
        if (optional.isPresent()) {
            var list = optional.get().map(this::buildUser).toList();
            users.setUsers(list);
        }
        return users;
    }

    @Transactional
    public Response updateUser(UserRecord userRecord) {
        var response = new Response();
        var optional = userRepository.findById(userRecord.login());
        if (optional.isPresent()) {
            var user = optional.get();
            user.setStatus(userRecord.status());
            user.setEndDate(new Timestamp(userRecord.endDate().longValue()));
            user.setUpdateDate(getCurrentTime());
            user.setEmail(userRecord.email());
            user.setClient(userRecord.client().intValue());
            user.setArea(userRecord.area().intValue());
            user.setAccessId(userRecord.access().intValue());
            if (userRecord.password()!=null && !userRecord.password().isEmpty()){
                user.setPassword(passwordEncoder.encode(userRecord.password()));
            }
            if (userRecord.status()=='R'){
                user.setRevokeDate(getCurrentTime());
            }
            userRepository.save(user);
        } else {
            response.setErrorMessage(messagesProperties.getUserNotFound().formatted(userRecord.login()));
        }
        return response;
    }

    @Transactional
    public Response revokeUser(String idUser) {
        var response = new Response();
        var optional = userRepository.findById(idUser);
        if (optional.isPresent()) {
            var user = optional.get();
            var currentDate = getCurrentTime();
            user.setStatus('R');
            user.setRevokeDate(currentDate);
            user.setUpdateDate(getCurrentTime());
        } else {
            response.setErrorMessage(messagesProperties.getUserNotFound().formatted(idUser));
        }
        return response;
    }

    private Timestamp getCurrentTime() {
        var calendar = Calendar.getInstance();
        return new Timestamp(calendar.getTimeInMillis());
    }


    private User buildUser(UserEntity userEntity) {
        long endDate = 0;
        if (userEntity.getEndDate() != null) {
            endDate = userEntity.getEndDate().getTime();
        }
        String name = "%s %s %s".formatted(userEntity.getName(), userEntity.getPaternal(), userEntity.getMaternal());
        return new User(userEntity.getUser(), name, userEntity.getStartDate().getTime(), endDate, userEntity.getStatus());

    }

    private UserEntity buildUserEntity(UserRecord userRecord) {
        var userEntity = new UserEntity();
        userEntity.setUser(userRecord.login());
        userEntity.setPassword(passwordEncoder.encode(userRecord.password()));
        userEntity.setName(userRecord.name());
        userEntity.setPaternal(userRecord.paternal());
        userEntity.setMaternal(userRecord.maternal());
        userEntity.setEmail(userRecord.email());
        userEntity.setEndDate(new Timestamp(userRecord.endDate().longValue()));
        userEntity.setAccessId(userRecord.access().intValue());
        userEntity.setArea(userRecord.area().intValue());
        userEntity.setClient(userRecord.client().intValue());
        userEntity.setStatus('A');
        return userEntity;
    }

    private UserRecord buildUserRecord(UserEntity userEntity) {

        long endDate = 0;
        if (userEntity.getEndDate() != null) {
            endDate = userEntity.getEndDate().getTime();
        }
        return new UserRecord(
                userEntity.getUser(),
                "",
                userEntity.getName(),
                userEntity.getPaternal(),
                userEntity.getMaternal(),
                userEntity.getClient(),
                userEntity.getStartDate().getTime(),
                endDate,
                userEntity.getEmail(),
                userEntity.getArea(),
                userEntity.getAccessId(),
                userEntity.getStatus()
        );
    }
}
