package com.docio.docio.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginCodeRepository loginCodeRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;
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
    public String initiateLogin(@Valid @RequestBody AuthController.LoginForm form){
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

    @PostMapping("/signin")
    public String completeLogin(@Valid @RequestBody AuthController.LoginForm form) {

        if (form.code == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code is null!");
        }
        LoginCode loginCode = loginCodeRepository.findByUserEmailAndCode(form.email, form.code);
        if (loginCode == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email code does not exist!");
        }
        String token = generateToken();
        saveToken(token, loginCode.getUser());
        return token;
    }

    private String generateToken(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    private void saveToken(String token, User user) {
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUser(user);
        userToken.setCreatedAt(Instant.now());
        try{
            userToken = userTokenRepository.save(userToken);//communicates with db
        }catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token is duplicated!");
        }
    }


    public static class LoginForm {
        @NotNull
        @Email String email;
        String code;
        public LoginForm(){}

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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
