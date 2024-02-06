package com.ssafy.tosi.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    // 즐겨찾기 등록
    public void insertFavorite (Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    // 즐겨찾기 삭제
    public void deleteFavorite (Integer favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

    // 즐겨찾기 목록 조회
    public List<Favorite> selectFavoriteList (Integer userId) {
        List<Favorite> favoriteList = favoriteRepository.findByUserId(userId);
        System.out.println(favoriteList);
        return favoriteList;
    }


}
