package com.LetMeDoWith.LetMeDoWith.common;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final boolean successYn;
    private final String statusCode;
    private final T data;
    private final String message;

    private ApiResponse(boolean successYn, String statusCode, T data, String message) {
        this.successYn = successYn;
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
    }


    /**
     * 성공한 응답 (OK, 조회 결과 없음) 에 사용한다.
     *
     * @param apiResponseStatus
     * @param data
     * @param <T>
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> successResponse(ApiResponseSuccessStatus apiResponseStatus, T data) {
        return new ApiResponse<>(true, apiResponseStatus.getCode(), data, apiResponseStatus.toString());
    }

    /**
     * 성공한 응답에 사용한다. payload 없이 성공 여부만을 응답한다.
     *
     * @param apiResponseStatus
     * @param <T>
     * @return
     */

    public static <T> ApiResponse<T> successResponse(ApiResponseSuccessStatus apiResponseStatus) {
        return new ApiResponse<>(true, apiResponseStatus.getCode(), null, apiResponseStatus.toString());
    }

    /**
     * 성공한 응답에 사용한다. 메세지를 변경할 수 있다.
     *
     * @param apiResponseStatus
     * @param data
     * @param message
     * @param <T>
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> successResponse(ApiResponseSuccessStatus apiResponseStatus, T data, String message) {
        return new ApiResponse<>(true, apiResponseStatus.getCode(), data, message);
    }


    /**
     * 실패한 응답에 사용한다.
     *
     * @param apiResponseStatus
     * @param <T>
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> failResponse(ApiResponseFailStatus apiResponseStatus) {
        return new ApiResponse<>(false, apiResponseStatus.getCode(), null, apiResponseStatus.toString());
    }


    /**
     * 실패한 응답에 사용한다. 메세지를 변경할 수 있다.
     *
     * @param apiResponseStatus
     * @param message
     * @param <T>
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> failResponse(ApiResponseFailStatus apiResponseStatus, String message) {
        return new ApiResponse<>(false, apiResponseStatus.getCode(), null, message);
    }
}
