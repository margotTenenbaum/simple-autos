package com.galvanize.autos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        } else if (color != null && make == null) {
            autosList = autoService.getAllAutosByColor(color);
        } else if (color == null && make != null) {
            autosList = autoService.getAllAutosByMake(make);
        } else {
            autosList = autoService.getAllAutos(color, make);
        }
        return autosList.isEmpty() ? ResponseEntity.noContent().build()
                                   : ResponseEntity.ok(autosList);
    }

    @PostMapping
    public Auto addAuto(@RequestBody Auto newAuto) {
        return autoService.addAuto(newAuto);
    }

    @GetMapping("/{vin}")
    public ResponseEntity<Auto> getAuto(@PathVariable String vin) {
        Auto auto = autoService.getAuto(vin);
        return auto == null ? ResponseEntity.noContent().build()
                            : ResponseEntity.ok(auto);
    }

    @PatchMapping("/{vin}")
    public ResponseEntity<Auto> updateAuto(@PathVariable String vin, @RequestBody UpdateAuto update) {
        //catch incorrect update obj

        Auto auto = autoService.updateAuto(vin, update.getColor(), update.getOwner());
        return auto == null ? ResponseEntity.noContent().build()
                            : ResponseEntity.ok(auto);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidAutoExceptionHandler(InvalidAutoException e) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidUpdateAutoExceptionHandler(InvalidUpdateAutoException e) {
    }

}
