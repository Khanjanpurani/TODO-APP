package org.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.pojos.Account;
import org.todo.pojos.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Integer> {

    Optional<VerificationToken> findByToken(String token);

    List<VerificationToken> findByAccountAndUsedFalseAndInvalidatedFalse(Account account);
}
