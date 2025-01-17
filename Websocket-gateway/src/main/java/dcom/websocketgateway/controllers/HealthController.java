package dcom.websocketgateway.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthController {


    @GetMapping
    public ResponseEntity<String> health() {

        return ResponseEntity.ok("OK");
    }
}
