package com.galvanize.autos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/autos")
public class AutosController {
    AutoService autoService;

    public AutosController(AutoService autoSerivce) {
        this.autoService = autoSerivce;
    }

    @GetMapping
    public AutosList getAutos() {
        return autoService.getAllAutos();
    }
}
