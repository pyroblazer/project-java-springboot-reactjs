package com.onlinestore.payload;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class ApiResponseTest {

    @Test
    public void testApiResponse() {
        ApiResponse successResponse = new ApiResponse("Success!");
        ApiResponse errorResponse = new ApiResponse("Error occurred.");

        System.out.println(successResponse); // Output: ApiResponse(message=Success!)
        System.out.println(errorResponse.getMessage()); // Output: Error occurred.

        successResponse.setMessage("Completed!");
        System.out.println(successResponse); // Output: ApiResponse(message=Completed!)
    }
}
