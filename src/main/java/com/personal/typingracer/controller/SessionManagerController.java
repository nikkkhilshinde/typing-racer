package com.personal.typingracer.controller;

import com.personal.typingracer.model.GameDetails;
import com.personal.typingracer.model.NewGameDto;
import com.personal.typingracer.model.response.EnterGameResponse;
import com.personal.typingracer.service.SessionManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author nikhilshinde on 26/09/22
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/game")
@CrossOrigin("*")
public class SessionManagerController {

    private final SessionManager sessionManager;

    private static final String USER_COOKIE_KEY = "user-id";

    @GetMapping
    public ResponseEntity<EnterGameResponse> getGameId(@RequestParam("requestId") String requestId) {

        log.info("Get game id request received with request-id {}", requestId);

        NewGameDto newGame = sessionManager.createNewGame();

//        setUserIdInCookie(newGame.getUserId(), response);

        return ResponseEntity.ok(
                EnterGameResponse.builder()
                        .success(Boolean.TRUE)
                        .gameDetails(new GameDetails(newGame.getGameId()))
                        .build());
    }

    private void setUserIdInCookie(String userId, HttpServletResponse response) {
        Cookie cookie = new Cookie(USER_COOKIE_KEY, userId);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
