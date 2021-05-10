package com.galvanize.autos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autos")
public class AutosController {
    @GetMapping
    public AutosList getAutos() {
        return null;
    }
}
