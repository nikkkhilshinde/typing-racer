package com.personal.typingracer.controller;

import com.personal.typingracer.model.ActiveSessionDto;
import com.personal.typingracer.model.User;
import com.personal.typingracer.model.response.EnterGameResponse;
import com.personal.typingracer.service.SessionManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author nikhilshinde on 26/09/22
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/session")
public class SessionManagerController {

    private final SessionManager sessionManager;

    @PostMapping("/enter")
    public ResponseEntity<EnterGameResponse> enterIntoGame(@RequestParam("requestId") String requestId, HttpServletResponse response) {
        User user = sessionManager.addUser();
        EnterGameResponse enterGameResponse = new EnterGameResponse();
        enterGameResponse.setData(new ActiveSessionDto(user.getGameId()));
        enterGameResponse.setSuccess(Boolean.TRUE);
        Cookie cookie = getCookie(user.getUserId());
        response.addCookie(cookie);
        return ResponseEntity.ok(enterGameResponse);
    }

    private Cookie getCookie(String userId){
        Cookie cookie = new Cookie("user-id", userId);
        cookie.setPath("/");
        return cookie;
    }
}
