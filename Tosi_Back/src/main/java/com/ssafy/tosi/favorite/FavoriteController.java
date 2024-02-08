package com.ssafy.tosi.favorite;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> postFavorite(HttpServletRequest request, @RequestBody Favorite favorite) {
        Integer userId = (Integer)request.getAttribute("userId");
        favorite.setUserId(userId);
        favoriteService.insertFavorite(favorite);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 즐겨찾기 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteFavorite(HttpServletRequest request, @RequestParam Integer favoriteId) {
        Integer userId = (Integer)request.getAttribute("userId");
        favoriteService.deleteFavorite(favoriteId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    // 즐겨찾기 목록 조회
    @GetMapping
    public ResponseEntity<?> getFavoritesList(HttpServletRequest request) {
        Integer userId = (Integer)request.getAttribute("userId");
        List<FavoriteDto> result = favoriteService.selectFavoriteList(userId);
        return new ResponseEntity<List<FavoriteDto>> (result, HttpStatus.OK);
    }
}
