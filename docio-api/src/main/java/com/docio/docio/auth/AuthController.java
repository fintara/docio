package com.docio.docio.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public Long signup(@Valid @RequestBody SignupForm form){
        User user = new User();
        user.setEmail(form.email);
        user.setCreatedAt(Instant.now());
        user = userRepository.save(user);
        return user.getId();
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
