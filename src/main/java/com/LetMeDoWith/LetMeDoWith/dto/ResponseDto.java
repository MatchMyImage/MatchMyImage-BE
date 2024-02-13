package com.LetMeDoWith.LetMeDoWith.dto;

import com.LetMeDoWith.LetMeDoWith.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.common.SuccessResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private final boolean successYn;
    private final String statusCode;
    private final String message;
    private final T data;

}
