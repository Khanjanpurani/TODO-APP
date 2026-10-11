package org.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.pojos.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {

    Optional<Account> findByEmail(String email);
}
