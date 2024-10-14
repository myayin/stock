package com.ing.market.exception;

import lombok.Getter;

@Getter
public class MarketException extends Exception {

    private final String errorCode;

    public MarketException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
