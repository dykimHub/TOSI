package com.ssafy.tosi.tale;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaleController {

    private final TaleService taleService;

    //TODO 프론트에서 인기순, 이름순 정렬 구현

    /**
     * 일반 정렬
     */
    @GetMapping("/tales")
    public ResponseEntity<List<Tale>> getAllTales() {
        return new ResponseEntity<>(taleService.selectAllTales(), HttpStatus.OK);
    }

    /**
     * 랜덤순 정렬
     */
    @GetMapping("/tales/random")
    public ResponseEntity<List<Tale>> getTalesOrderByRandom() {
        return new ResponseEntity<>(taleService.selectAllTalesOrderByRandom(), HttpStatus.OK);
    }

    /**
     * taleId 기준으로 하나만 조회
     */
    @GetMapping("/tale/{taleId}")
    public ResponseEntity<Tale> getTale(@PathVariable int taleId) {
        System.out.println(taleService.selectOneTale(taleId));
        return new ResponseEntity<>(taleService.selectOneTale(taleId), HttpStatus.OK);
    }

    /**
     * 이름으로 검색
     */
    @GetMapping("/tales/search")
    public ResponseEntity<List<Tale>> searchTale(@RequestParam String title) {
        return new ResponseEntity<>(taleService.selectByTitle(title), HttpStatus.OK);
    }
}