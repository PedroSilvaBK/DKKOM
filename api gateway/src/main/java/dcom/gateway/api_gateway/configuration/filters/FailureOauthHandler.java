package dcom.gateway.api_gateway.configuration.filters;

import dcom.gateway.api_gateway.configuration.jwt_token.JwtTokenProvider;
import dcom.gateway.api_gateway.domain.RegisterUserRequest;
import dcom.gateway.api_gateway.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class FailureOauthHandler {
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${frontend.auth.callback.url}")
    private String callbackUrl;

    @Value("${user.service.host}")
    private String userServiceUrl;

    @Value("${prod.cookie}")
    private boolean prodCookie;


    @Bean
    public ServerAuthenticationFailureHandler serverAuthenticationFailureHandler() {
        return (webFilterExchange, exception) -> {
            ServerWebExchange exchange = webFilterExchange.getExchange();

            // Log the failure reason
            log.error("Authentication failure: {}", exception.getMessage(), exception);

            // Redirect to the error page with a reason query parameter
            String failureReason = exception.getMessage() != null ? exception.getMessage() : "unknown_error";

            log.debug("Redirecting to error URL: {}", failureReason);

            exchange.getResponse().setStatusCode(HttpStatus.FOUND);

            String selectedUrl = prodCookie ? "https://dkkom.com/login" : "http://localhost:5173login";
            exchange.getResponse().getHeaders().setLocation(URI.create(selectedUrl));

            return exchange.getResponse().setComplete();
        };
    }
}
