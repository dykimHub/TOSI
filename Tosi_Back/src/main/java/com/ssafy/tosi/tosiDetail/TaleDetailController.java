package com.ssafy.tosi.tosiDetail;

import com.ssafy.tosi.tale.Tale;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
//@RequestMapping("/tosi")
@RestController
public class TaleDetailController {

    private final TaleDetailService taleDetailService;

    @GetMapping("/taledetails/{taleId}")
    public ResponseEntity<?> getTaleDetail(@PathVariable int taleId) {
        try {
            Tale tale = taleDetailService.getTaleDetail(taleId);
            return new ResponseEntity<Tale>(tale, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
