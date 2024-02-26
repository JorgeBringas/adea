package com.adea.exam.management;

import com.adea.exam.management.controller.beans.UserFilterRequest;
import com.adea.exam.management.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Repository
public class UsersDao {

    @PersistenceContext
    private EntityManager entityManager;


    public Optional<Stream<UserEntity>> getUsers(UserFilterRequest userFilterRequest) {
        Stream<UserEntity> stream = null;
        try {
            String query = getQuery(userFilterRequest);
            TypedQuery<UserEntity> typedQuery = entityManager.createQuery(query, UserEntity.class);
            setParams(typedQuery, userFilterRequest);
            typedQuery.getResultStream();
            stream = typedQuery.getResultStream();
        } catch (NoResultException e) {

        }
        return Optional.ofNullable(stream);
    }

    private void setParams(TypedQuery<UserEntity> typedQuery, UserFilterRequest userFilterRequest) {
        typedQuery.setParameter("start", new Date(userFilterRequest.getDate1()));
        typedQuery.setParameter("end", new Date(userFilterRequest.getDate2()));

        if (userFilterRequest.getName() != null && !userFilterRequest.getName().isEmpty()) {
            typedQuery.setParameter("name", userFilterRequest.getName());
        }
        if (!userFilterRequest.getStatus().equals("X")) {
            typedQuery.setParameter("status", userFilterRequest.getStatus().charAt(0));
        }
    }

    private String getQuery(UserFilterRequest userFilterRequest) {
        StringBuilder query = new StringBuilder("Select c from UserEntity c ");
        query.append("where c.startDate between :start and :end ");

        if (userFilterRequest.getName() != null && !userFilterRequest.getName().isEmpty()) {
            query.append("and UPPER(CONCAT(c.name,' ',c.paternal,' ',c.maternal)) like UPPER(CONCAT('%',:name,'%')) ");
        }

        if (!userFilterRequest.getStatus().equals("X")) {
            query.append("and c.status = :status");
        }

        return query.toString();
    }
}
