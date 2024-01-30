package com.ssafy.tosi.taleDetail;

import com.ssafy.tosi.tale.TaleDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class TaleDetailController {

    private final TaleDetailService taleDetailService;

    @GetMapping("/tales/{taleId}")
    public ResponseEntity<?> getTaleDetail(@PathVariable int taleId) {
        try {
            TaleDto taleDto = taleDetailService.getTaleDetail(taleId);
            return new ResponseEntity<TaleDto>(taleDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            String errMsg = "No Data";
            return new ResponseEntity<String>(errMsg, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/tales/{CName}/{BName}")
    public ResponseEntity<?> selectName(@PathVariable String CName, @PathVariable String BName) {
        try {
            taleDetailService.selectName(CName, BName);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/tales/change")
    public ResponseEntity<?> changeName(@RequestBody TaleDto taleDto) {
        try {
            String[] splitted_contents = taleDetailService.split_sentences(taleDto);
            String[] changedContents = new String[splitted_contents.length];
            changedContents = taleDetailService.changeName(splitted_contents);
            return new ResponseEntity<String[]>(changedContents, HttpStatus.OK);
        } catch (IOException e) {
            String errMsg = "File Changing Error";
            return new ResponseEntity<String>(errMsg, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
