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
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginCodeRepository loginCodeRepository;
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
    }

    @PostMapping("/init")
    public String initiateLogin(@Valid @RequestBody LoginInItForm form){
        User user = userRepository.findByEmail(form.email);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        }else{
        String code = generateLoginCode();
        saveLoginCode(code, user);
        sendLoginCode(code, form.email);
        return "";
        }
    }

    private void sendLoginCode(String code, String email) {
        System.out.println(code + " " + email);
    }

    private void saveLoginCode(String code, User user) {
        LoginCode loginCode = new LoginCode();
        loginCode.setCode(code);
        loginCode.setUser(user);
        loginCode.setCreatedAt(Instant.now());
        try{
            loginCode = loginCodeRepository.save(loginCode);//communicates with db
        }catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One user should have one code");
        }
    }

    private String generateLoginCode() {
        int upperBound = 1000000;
        Random random = new Random();
        int rand = random.nextInt(upperBound);
        return String.valueOf(rand);
    }

    public static class LoginInItForm{
        @NotNull
        @Email String email;

        public LoginInItForm(){}

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
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
