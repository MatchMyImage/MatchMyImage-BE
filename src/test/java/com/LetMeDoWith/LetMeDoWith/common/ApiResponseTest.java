package com.LetMeDoWith.LetMeDoWith.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    @DisplayName("성공 응답시")
    void test_response_success() {
        ApiResponse<String> apiResponse = ApiResponse.successResponse(ApiResponseSuccessStatus.OK, "Test Data");

        assertTrue(apiResponse.isSuccessYn());
        assertEquals(apiResponse.getData(), "Test Data");
        assertEquals(apiResponse.getStatusCode(), "OK");
        assertEquals(apiResponse.getMessage(), "성공적으로 조회하였습니다.");
    }

    @Test
    @DisplayName("성공 응답시 - payload 없음")
    void test_response_success_no_data() {
        ApiResponse<String> apiResponse = ApiResponse.successResponse(ApiResponseSuccessStatus.OK);

        assertTrue(apiResponse.isSuccessYn());

        // payload is null
        assertNull(apiResponse.getData());
        
        assertEquals(apiResponse.getStatusCode(), "OK");
        assertEquals(apiResponse.getMessage(), "성공적으로 조회하였습니다.");
    }

    @Test
    @DisplayName("성공 응답시 - 메세지 변경 시")
    void test_response_success_custom_message() {
        ApiResponse<String> apiResponse = ApiResponse.successResponse(ApiResponseSuccessStatus.OK, "Test Data", "custom message");

        assertTrue(apiResponse.isSuccessYn());
        assertEquals(apiResponse.getData(), "Test Data");
        assertEquals(apiResponse.getStatusCode(), "OK");

        // with custom message
        assertEquals(apiResponse.getMessage(), "custom message");
    }

    @Test
    @DisplayName("실패 응답 시")
    void test_response_fail() {
        ApiResponse<Object> apiResponse = ApiResponse.failResponse(ApiResponseFailStatus.INVALID_PARAM_ERROR);

        assertFalse(apiResponse.isSuccessYn());
        assertNull(apiResponse.getData());
        assertEquals(apiResponse.getStatusCode(), "INVALID_PARAM_ERROR");
        assertEquals(apiResponse.getMessage(), "요청 파라미터가 잘못되었습니다.");
    }

    @Test
    @DisplayName("실패 응답 시 - 메세지 변경 시")
    void test_response_fail_custom_message() {
        ApiResponse<Object> apiResponse = ApiResponse.failResponse(ApiResponseFailStatus.INVALID_PARAM_ERROR, "custom message");

        assertFalse(apiResponse.isSuccessYn());
        assertNull(apiResponse.getData());
        assertEquals(apiResponse.getStatusCode(), "INVALID_PARAM_ERROR");

        // with custom message
        assertEquals(apiResponse.getMessage(), "custom message");
    }
}