package com.personal.typingracer.controller;

import com.personal.typingracer.model.ActiveSessionDto;
import com.personal.typingracer.model.response.EnterGameResponse;
import com.personal.typingracer.service.SessionManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nikhilshinde on 26/09/22
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/session")
public class GameSessionManagerController {

    private final SessionManager sessionManager;

    @PostMapping("/enter")
    public ResponseEntity<EnterGameResponse> enterIntoGame(@RequestParam("requestId") String requestId) {
        String sessionId = sessionManager.addUser();
        EnterGameResponse enterGameResponse = new EnterGameResponse();
        enterGameResponse.setData(new ActiveSessionDto(sessionId));
        enterGameResponse.setSuccess(Boolean.TRUE);
        return ResponseEntity.ok(enterGameResponse);
    }
}
