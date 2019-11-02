package com.eric.springsecurity;

import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {
    /**
     * 对原始密码进行加密
     */
    @Override
    public String encode(CharSequence rawPassword) {
        PasswordEncoder encoder = new SCryptPasswordEncoder();
        return encoder.encode(rawPassword);
    }

    /**
     * 将原始密码与数据库中的密码进行匹配
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        PasswordEncoder encoder = new SCryptPasswordEncoder();
        String encodedRawPassword = encoder.encode(rawPassword);
        return rawPassword != null && rawPassword.equals(encodedPassword);
    }
}
