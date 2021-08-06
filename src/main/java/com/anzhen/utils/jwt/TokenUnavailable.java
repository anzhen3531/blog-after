package com.anzhen.utils.jwt;

/**
 * @Classname : TokenUnavailable
 * @Date : 2021.5.11 10:26
 * @Created : anzhen
 * @Description :
 * @目的:
 */
public class TokenUnavailable extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TokenUnavailable(String message) {
        super(message);
    }
}
