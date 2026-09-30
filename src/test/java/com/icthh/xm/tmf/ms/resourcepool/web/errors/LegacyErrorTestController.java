package com.icthh.xm.tmf.ms.resourcepool.web.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
public class LegacyErrorTestController {

    @GetMapping("/test/legacy-errors/param")
    public String param(@RequestParam("name") String name) {
        return name;
    }

    @GetMapping("/test/legacy-errors/upstream")
    public void upstream() {
        throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "bad gateway");
    }
}
