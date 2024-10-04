package com.retotcs.demodevops;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.retotcs.demodevops.controller.DevOpsController;
import com.retotcs.demodevops.service.JwtUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DevOpsController.class)
class DemodevopsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testHandleDevOpsRequest_ValidHeaders() throws Exception {
      
        String validJwt = jwtUtil.generateToken("Rita Asturia");

        String jsonRequest = "{ \"message\": \"This is a test\", \"to\": \"Juan Perez\", \"from\": \"Rita Asturia\", \"timeToLifeSec\": 45 }";

        mockMvc.perform(post("/devops")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Parse-REST-API-Key", "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c")
                .header("X-JWT-KWY", validJwt)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(jsonPath("$.message").value("Hello Juan Perez your message will be send")); 
    }

    @Test
    public void testHandleDevOpsRequest_InvalidApiKey() throws Exception {
        String validJwt = "valid.jwt.token";

        String jsonRequest = "{ \"message\": \"This is a test\", \"to\": \"Juan Perez\", \"from\": \"Rita Asturia\", \"timeToLifeSec\": 45 }";

        mockMvc.perform(post("/devops")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Parse-REST-API-Key", "invalid-api-key")
                .header("X-JWT-KWY", validJwt)
                .content(jsonRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("ERROR: invalid-api-key"));
    }

    @Test
    public void testHandleDevOpsRequest_InvalidJwt() throws Exception {
        String invalidJwt = "invalid.jwt.token";

        String jsonRequest = "{ \"message\": \"This is a test\", \"to\": \"Juan Perez\", \"from\": \"Rita Asturia\", \"timeToLifeSec\": 45 }";

        mockMvc.perform(post("/devops")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Parse-REST-API-Key", "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c")
                .header("X-JWT-KWY", invalidJwt)
                .content(jsonRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("ERROR: Invalid or expired JWT"));
    }

}
