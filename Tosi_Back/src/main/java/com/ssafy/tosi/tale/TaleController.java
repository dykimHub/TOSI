package com.ssafy.tosi.tale;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@CrossOrigin
public class TaleController {

    private final TaleService taleService;

    /**
     * 일반 정렬
     */
    @GetMapping("/tales")
    public ResponseEntity<List<TaleDto>> getAllTales() {
        return new ResponseEntity<>(taleService.selectAllTales(), HttpStatus.OK);
    }

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
    @GetMapping("/search")
    public ResponseEntity<?> searchTale(@RequestParam(required = false) String title) {
        try {
            if(title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("검색어를 입력하세요.");
            }

            List<Tale> list = taleService.selectByTitle(title);

            if(list.isEmpty()) {
                throw new NoTalesFoundException("일치하는 결과를 찾을 수 없습니다.");
            } else {
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
        } catch (IllegalArgumentException  e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch ( NoTalesFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}