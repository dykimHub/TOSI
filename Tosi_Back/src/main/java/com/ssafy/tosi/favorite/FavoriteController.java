package com.ssafy.tosi.favorite;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/favorites")
@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;

    // 즐겨찾기 등록
    @PostMapping
    public ResponseEntity<?> postFavorite(@RequestBody Favorite favorite) {
        favoriteService.insertFavorite(favorite);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 즐겨찾기 삭제
    @DeleteMapping
    public ResponseEntity<?> deteleFavorite(@RequestParam Integer favoriteId) {
        favoriteService.deleteFavorite(favoriteId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 즐겨찾기 목록 조회
    @GetMapping
    public ResponseEntity<?> getFavoritesList(@RequestParam Integer userId) {
        List<Favorite> result = favoriteService.selectFavoriteList(userId);
        return new ResponseEntity<List<Favorite>> (result, HttpStatus.OK);
    }
}
