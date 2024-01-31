package com.LetMeDoWith.LetMeDoWith.Common;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final boolean successYn;
    private final ApiResponseStatus statusCode;
    private final T data;
    private final String message;

    private ApiResponse(boolean successYn, ApiResponseStatus statusCode, T data, String message) {
        this.successYn = successYn;
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
    }


    // TODO: 기본 Exception이 아닌 다른 checked-exception 을 정의해서 바꿔야 함


    /**
     * 성공한 응답 (OK, 조회 결과 없음) 에 사용한다.
     *
     * @param apiResponseStatus
     * @param data
     * @param <T>
     * @return ApiResponse
     * @throws Exception
     */
    public static <T> ApiResponse<T> successResponse(ApiResponseStatus apiResponseStatus, T data) throws Exception {
        if (apiResponseStatus.isFailResponse()) {
            throw new Exception("You tried sending response via successResponse() with failed request. Please respond with failResponse().");
        }

        return new ApiResponse<>(true, apiResponseStatus, data, apiResponseStatus.toString());
    }

    /**
     * 성공한 응답에 사용한다. 메세지를 변경할 수 있다.
     *
     * @param apiResponseStatus
     * @param data
     * @param message
     * @param <T>
     * @return ApiResponse
     * @throws Exception
     */
    public static <T> ApiResponse<T> successResponseWithMessage(ApiResponseStatus apiResponseStatus, T data, String message) throws Exception {
        if (apiResponseStatus.isFailResponse()) {
            throw new Exception("You tried sending response via successResponse() with failed request. Please respond with failResponse().");
        }

        return new ApiResponse<>(true, apiResponseStatus, data, message);
    }

    /**
     * 실패한 응답에 사용한다.
     *
     * @param apiResponseStatus
     * @param <T>
     * @return ApiResponse
     * @throws Exception
     */
    public static <T> ApiResponse<T> failResponse(ApiResponseStatus apiResponseStatus) throws Exception {
        if (!apiResponseStatus.isFailResponse()) {
            throw new Exception("You tried sending response via failResponse() with succeeded request. Please respond with successResponse().");
        }

        return new ApiResponse<>(false, apiResponseStatus, null, apiResponseStatus.toString());
    }

    /**
     * 실패한 응답에 사용한다. 메세지를 변경할 수 있다.
     *
     * @param apiResponseStatus
     * @param message
     * @param <T>
     * @return ApiResponse
     * @throws Exception
     */
    public static <T> ApiResponse<T> failResponseWithMessage(ApiResponseStatus apiResponseStatus, String message) throws Exception {
        if (!apiResponseStatus.isFailResponse()) {
            throw new Exception("You tried sending response via failResponse() with succeeded request. Please respond with successResponse().");
        }

        return new ApiResponse<>(false, apiResponseStatus, null, message);
    }
}
