package org.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todo.pojos.Account;
import org.todo.pojos.VerificationToken;
import org.todo.repository.VerificationTokenRepository;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

@Service
public class TokenService {

    private final SecureRandom secureRandom = new SecureRandom();
    private final VerificationTokenRepository verificationTokenRepository;


    public TokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }


    public String generateVerificationToken() {

        byte[] randomBytes = new byte[32];

        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    public boolean validateToken(VerificationToken verificationToken){

        if((verificationToken.isInvalidated() || verificationToken.isUsed())){
            return false;
        }
        if (!Instant.now().isBefore(verificationToken.getExpiresAt())) {
            return false;
        }
        return true;
    }

    @Transactional
    public void invalidatePreviousTokens(Account account) {
        List<VerificationToken> tokens =
                verificationTokenRepository.findByAccountAndUsedFalseAndInvalidatedFalse(account);

        for (VerificationToken token : tokens) {
            token.setInvalidated(true);
        }

        verificationTokenRepository.saveAll(tokens);
    }

}