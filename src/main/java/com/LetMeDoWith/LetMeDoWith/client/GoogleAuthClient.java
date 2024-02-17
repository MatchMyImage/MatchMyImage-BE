package com.LetMeDoWith.LetMeDoWith.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleAuthClient {

	private final WebClient webClient;
}
