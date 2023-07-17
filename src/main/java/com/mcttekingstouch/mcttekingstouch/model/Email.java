package com.mcttekingstouch.mcttekingstouch.model;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Email{" +
                "address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
