package com.mycom.myapp.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
// 주소
public class Address {

    @Column(name = "zip_code", length = 5, nullable = false)
    private String zipCode;

    @Column(name = "first_address", nullable = false)
    private String firstAddress;

    @Column(name = "last_address", nullable = false)
    private String lastAddress;

    protected Address() {}

    public Address(String zipCode, String firstAddress, String lastAddress) {
        this.zipCode = zipCode;
        this.firstAddress = firstAddress;
        this.lastAddress = lastAddress;
    }
}
