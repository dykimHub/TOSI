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

    //TODO 프론트에서 정렬
    //기본 조회는 랜덤순
    @GetMapping("/tales")
    public ResponseEntity<List<Tale>> getTalesOrderByRandom() {
        return new ResponseEntity<>(taleService.selectAllTalesOrderByRandom(), HttpStatus.OK);
    }

    //인기순
    @GetMapping("/tales?sort=popularity")
    public ResponseEntity<List<Tale>> getTalesOrderByLikeCnt() {
        return new ResponseEntity<>(taleService.selectAllTalesOrderByLikeCnt(), HttpStatus.OK);
    }

    //이름순
    @GetMapping("/tales?sort=title")
    public ResponseEntity<List<Tale>> getTalesOrderByTitle() {
        return new ResponseEntity<>(taleService.selectAllTalesOrderByTitle(), HttpStatus.OK);
    }

    //수정: rest api 방식으로
    //수정: @Controller -> @RestController

    /**
     * taleId 기준으로 하나만 조회
     */
    @GetMapping("/tale/{taleId}")
    public ResponseEntity<Tale> getTale(@PathVariable int taleId) {
        return new ResponseEntity<>(taleService.selectOneTale(taleId), HttpStatus.OK);
    }

    /**
     * 이름으로 검색
     */
    @GetMapping("/tales/search")
    //수정: PathVariable -> @RequestParam, url 변경
    public ResponseEntity<List<Tale>> searchTale(@RequestParam String title) {
        return new ResponseEntity<>(taleService.selectByTitle(title), HttpStatus.OK);
    }
}