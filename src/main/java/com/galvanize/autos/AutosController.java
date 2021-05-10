package com.galvanize.autos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<AutosList> getAutos(@RequestParam(required = false) String color,
                                              @RequestParam(required = false) String make) {
        AutosList autosList;
        if (color == null && make == null) {
            autosList = autoService.getAllAutos();
        } else {
            autosList = autoService.getAllAutos(color, make);
        }
        return autosList.isEmpty() ? ResponseEntity.noContent().build()
                                   : ResponseEntity.ok(autosList);
    }
}
