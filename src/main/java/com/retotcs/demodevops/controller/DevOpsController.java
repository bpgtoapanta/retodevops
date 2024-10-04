package com.retotcs.demodevops.controller;

import com.retotcs.demodevops.model.DevOpsRequest;
import com.retotcs.demodevops.model.DevOpsResponse;
import com.retotcs.demodevops.service.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("/devops")
public class DevOpsController {

    private final String API_KEY = "2f5ae96c-b558-4c7b-a590-a501ae1c3f6c";

    private final JwtUtil jwtUtil;

    public DevOpsController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<Object> sendMessage(@RequestHeader("X-Parse-REST-API-Key") String apiKey,
                                              @RequestHeader("X-JWT-KWY") String jwt,
                                              @Valid @RequestBody DevOpsRequest request) {
        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR: invalid-api-key");
        }

        if (!jwtUtil.validateToken(jwt, request.getFrom())) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR: Invalid or expired JWT");
        }

        return ResponseEntity.ok(new DevOpsResponse("Hello " + request.getTo() + " your message will be send"));
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<String> handleOtherRequests() {
      return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("ERROR");
    }

    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestBody Map<String, String> requestBody) {
      String username = requestBody.get("username"); 
      String token = jwtUtil.generateToken(username);
      return ResponseEntity.ok(token);
  }
}
