package org.todo.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todo.dto.RegisterRequest;
import org.todo.dto.ResendVerificationRequest;
import org.todo.pojos.VerificationToken;
import org.todo.repository.VerificationTokenRepository;
import org.todo.service.AccountService;
import org.todo.service.TokenService;

import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
public class AccountController {

    private final AccountService accountService;
    private final TokenService tokenService;
    private final VerificationTokenRepository verificationTokenRepository;

    public AccountController(AccountService accountService, TokenService tokenService, VerificationTokenRepository verificationTokenRepository) {
        this.accountService = accountService;
        this.tokenService = tokenService;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @PostMapping("/register")
    ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {

        accountService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Account registered successfully");
    }


    @GetMapping(value = "/verify-email")
    ResponseEntity<String>verifyEmail(@RequestParam (value = "token")String token){
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken.isPresent()) {
            if(tokenService.validateToken(verificationToken.get())){
                accountService.setEmailVerifiedAndTokenAsUsed(verificationToken.get());
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Email verification successfully");
            }else {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Email verification failed please try again");
            }
        }else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Email verification failed please try again");
        }

    }

    @PostMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(@Valid @RequestBody ResendVerificationRequest request) {

        accountService.resendVerification(request.getEmail());

        return ResponseEntity.ok(
                "If the account exists and needs verification, "
                        + "a verification email will be sent."
        );
    }
}
