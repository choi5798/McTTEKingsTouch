package com.mcttekingstouch.mcttekingstouch.model;

public class Email {
    private final String address;

    public Email(String address) {
        validate(address);
        this.address = address;
    }

    private void validate(String address) {
        if (!address.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b")) {
            throw new IllegalArgumentException("올바르지 않은 이메일 형식입니다");
        }
    }


}
