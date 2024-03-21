package com.docio.docio.auth;

import org.springframework.data.repository.CrudRepository;

public interface LoginCodeRepository extends CrudRepository<LoginCode, Long> {
    public LoginCode findByUserEmailAndCode(String email, String code);
}
