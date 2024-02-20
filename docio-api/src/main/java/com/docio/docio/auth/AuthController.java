package com.docio.docio.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@Valid @RequestBody SignupForm form){
        User user = new User();
        user.setEmail(form.email);
        user.setCreatedAt(Instant.now());
        try{
            user = userRepository.save(user);//communicates with db
            return ResponseEntity.ok(user.getId());
        }catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        /*catch(SQLIntegrityConstraintViolationException e){
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }*/
    }

    public static class SignupForm{
        @NotNull
        @Email String email;

        public SignupForm(){}

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


}
