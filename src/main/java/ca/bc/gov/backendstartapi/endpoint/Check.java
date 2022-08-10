package ca.bc.gov.backendstartapi.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Check {

    @GetMapping("/health-check")
    public String check() {
        return "OK";
    }
}
