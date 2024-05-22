package com.winterhold.dao;

import com.winterhold.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("""
    SELECT COUNT (acc.username)
    FROM Account AS acc
    WHERE acc.username = :username
    """)
    public Integer count (@Param("username") String username);
}
