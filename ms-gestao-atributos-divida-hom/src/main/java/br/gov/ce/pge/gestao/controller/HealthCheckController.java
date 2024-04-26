package br.gov.ce.pge.gestao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "health-check")
public class HealthCheckController {

    @GetMapping()
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body("OK! - v0.0.24");
    }
}
