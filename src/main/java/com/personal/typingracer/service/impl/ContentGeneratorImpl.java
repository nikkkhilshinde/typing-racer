package com.personal.typingracer.service.impl;

import com.personal.typingracer.model.Content;
import com.personal.typingracer.model.thirdparty.ContentResponse;
import com.personal.typingracer.service.ContentGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author nikhilshinde on 05/10/22
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ContentGeneratorImpl implements ContentGenerator {

    @Value("${third-party.content.api}")
    private String fetchContentUrl;

    @Value("${third-party.content.limit}")
    private int limit;

    @Value("${game.config.words-lower-count}")
    private int wordsLowerCount;

    private final RetryTemplate retryTemplate;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Content generateContent() {
        ContentResponse contentResponse = retryTemplate.execute((context) -> {
            ResponseEntity<ContentResponse> exchange = restTemplate
                    .getForEntity(generateUri(), ContentResponse.class);

            if (exchange.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(exchange.getBody()).isSuccess()) {
                return exchange.getBody();
            }

            return null;
        });

        if (contentResponse == null) {
            return new Content("");
        }

        List<String> words = new ArrayList<>();
        for (String fact : contentResponse.getFacts()) {
            String[] factWords = fact.split(" ");
            while (words.size() <= wordsLowerCount) {
                words.addAll(Arrays.asList(factWords));
            }
        }

        StringBuilder content = new StringBuilder();
        words.forEach(word -> content.append(word).append(" "));
        return new Content(content.toString());
    }

    private String generateUri() {
        return UriComponentsBuilder.fromHttpUrl(fetchContentUrl).queryParam("number", limit).encode().toUriString();
    }
}
