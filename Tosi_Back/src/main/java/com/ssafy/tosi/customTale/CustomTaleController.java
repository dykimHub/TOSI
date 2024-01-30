package com.ssafy.tosi.customTale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class CustomTaleController {

    private final CustomTaleService customTaleService;

    @Autowired
    public CustomTaleController(CustomTaleService customTaleService) {
        this.customTaleService = customTaleService;
    }

    @GetMapping("/customtale/{customTaleId}")
    public ResponseEntity<?> getCustomTale(@PathVariable Long customTaleId) {
            Optional<CustomTale> customTale = customTaleService.getCustomTale(customTaleId);
            return ResponseEntity.ok(customTale);
    }

    @GetMapping("/customtale/user/{userId}")
    public ResponseEntity<?> getCustomTalesByUserId(@PathVariable Long userId) {
            List<CustomTale> customTales = customTaleService.getCustomTalesByUserId(userId);
            return ResponseEntity.ok(customTales);
    }

    @GetMapping("/customtale")
    public ResponseEntity<?> getCustomTales() {
            List<CustomTale> customTales = customTaleService.getCustomTales();
            return ResponseEntity.ok(customTales);

    }

    @PostMapping("/customtale")
    public ResponseEntity<?> postCustomTale(@RequestBody CustomTale customTale) {
            CustomTale savedCustomTale = customTaleService.postCustomTale(customTale);
            return new ResponseEntity<>(savedCustomTale, HttpStatus.CREATED);

    }

    @PutMapping("/customtale/{customTaleId}")
    public ResponseEntity<?> putCustomTale(@PathVariable Long customTaleId, @RequestParam boolean isPublic) {
            CustomTale updatedCustomTale = customTaleService.putCustomTale(customTaleId, isPublic);
            return ResponseEntity.ok(updatedCustomTale);

    }

    @DeleteMapping("/customtale/{customTaleId}")
    public ResponseEntity<?> deleteCustomTale(@PathVariable Long customTaleId) {
        customTaleService.deleteCustomTale(customTaleId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}