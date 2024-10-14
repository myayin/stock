package com.ing.market.dto;

import static com.ing.market.constants.ResponseCode.SUCCESS;
import static com.ing.market.constants.ResponseCode.SYSTEM_ERROR;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Result implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result success() {
        return new Result(SUCCESS.name(), SUCCESS.getMessage());
    }

    public static Result error() {
        return new Result(SYSTEM_ERROR.name(), SYSTEM_ERROR.getMessage());
    }
}
