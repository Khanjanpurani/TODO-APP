package org.todo.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todo.dto.RegisterRequest;
import org.todo.exception.EmailAlreadyExistsException;
import org.todo.pojos.Account;
import org.todo.pojos.VerificationToken;
import org.todo.repository.AccountRepository;
import org.todo.repository.VerificationTokenRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final TokenService tokenService;
    private final EmailService emailService;

    public  AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository, TokenService tokenService, EmailService emailService){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    public void register(RegisterRequest request) {

        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        Account account = new Account();

        account.setName(request.getName());
        account.setEmail(request.getEmail());

        String passwordHash = passwordEncoder.encode(request.getPassword());

        account.setPasswordHash(passwordHash);
        account.setEmailVerified(false);

        accountRepository.save(account);

        String token = tokenService.generateVerificationToken();

        VerificationToken verificationToken = new VerificationToken();

        verificationToken.setToken(token);
        verificationToken.setAccount(account);
        verificationToken.setExpiresAt(
                Instant.now().plus(5, ChronoUnit.MINUTES)
        );
        verificationToken.setUsed(false);
        verificationToken.setInvalidated(false);

        verificationTokenRepository.save(verificationToken);

        emailService.sendVerificationEmail(
                account.getEmail(),
                account.getName(),
                token
        );
    }

    @Transactional
    public void setEmailVerifiedAndTokenAsUsed(VerificationToken verificationToken){
        Account account = verificationToken.getAccount();
        account.setEmailVerified(true);
        verificationToken.setUsed(true);
        accountRepository.save(account);
        verificationTokenRepository.save(verificationToken);
    }

    @Transactional
    public void resendVerification(String email) {

        Optional<Account> accountOptional = accountRepository.findByEmail(email);


        if (accountOptional.isEmpty() || accountOptional.get().isEmailVerified()) {
            return;
        }

        Account account = accountOptional.get();

        // Invalidate previous active tokens.
        tokenService.invalidatePreviousTokens(account);

        // Generate a fresh token.
        String token = tokenService.generateVerificationToken();
        VerificationToken verificationToken = new VerificationToken();

        verificationToken.setToken(token);
        verificationToken.setAccount(account);
        verificationToken.setExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES));
        verificationToken.setUsed(false);
        verificationToken.setInvalidated(false);

        verificationTokenRepository.save(verificationToken);

        // Send the new verification email.
        emailService.sendVerificationEmail(account.getEmail(), account.getName(), token);
    }

}
