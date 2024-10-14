package com.ing.market.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
@Getter
@Setter
public class BaseResponse {

    private Result result;

    public BaseResponse(final Result result) {
        this.result = result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
