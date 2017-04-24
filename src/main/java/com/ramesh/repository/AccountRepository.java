package com.ramesh.repository;

import com.ramesh.domain.Account;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByUsername(String username);
}
