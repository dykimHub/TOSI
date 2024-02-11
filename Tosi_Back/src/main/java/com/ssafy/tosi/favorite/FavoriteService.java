package com.ssafy.tosi.favorite;

import com.ssafy.tosi.s3.S3Service;
import com.ssafy.tosi.tale.Tale;
import com.ssafy.tosi.tale.TaleDto;
import com.ssafy.tosi.taleDetail.TaleDetailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final TaleDetailService taleDetailService;
    private final S3Service s3Service;

    // 즐겨찾기 등록
//    public void insertFavorite(Favorite favorite) {
//        favoriteRepository.save(favorite);
//    }
//
    // 즐겨찾기 삭제
    public void deleteFavorite(Integer favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }
//
//    // 즐겨찾기 목록 조회
//    public List<FavoriteDto> selectFavoriteList(Integer userId) {
//        List<Favorite> favoriteList = favoriteRepository.findByUserId(userId);
//        List<FavoriteDto> favoriteDtoList = new ArrayList<>();
//        for (Favorite favorite : favoriteList) {
//            Tale tale = favorite.getTale();
//            String[] images = tale.getImages().split(",");
//            for (int i = 0; i < images.length; i++)
//                images[i] = s3Service.getPath(images[i]);
//            String total_contents = "";
//            total_contents += tale.getContent1();
//            total_contents += tale.getContent2();
//            total_contents += tale.getContent3();
//            if (tale.getContent4() != null)
//                total_contents += tale.getContent4();
//            String[] characters = tale.getCharacters().split(",");
//            TaleDto taleDto = TaleDto.builder()
//                    .taleId(tale.getTaleId())
//                    .title(tale.getTitle())
//                    .content1(tale.getContent1())
//                    .content2(tale.getContent2())
//                    .content3(tale.getContent3())
//                    .content4(tale.getContent4())
//                    .total_contents(total_contents)
//                    .images(images)
//                    .thumbnail(images[0])
//                    .characters(characters)
//                    .time(tale.getTime())
//                    .likeCnt(tale.getLikeCnt())
//                    .build();
//            favoriteDtoList.add(new FavoriteDto(favorite.getFavoriteId(), favorite.getUserId(), taleDto));
//        }
//        return favoriteDtoList;
//    }

    public Favorite insertFavorite(Favorite favorite) {
        if (favorite == null)
            throw new EntityNotFoundException();
        return favoriteRepository.save(favorite);
    }

    public int getFavorite(int userId, int taleId) {
        return favoriteRepository.getFavorite(userId, taleId);
    }

    public List<TaleDto> getFavoriteList(int userId) {
        List<Favorite> favorites = favoriteRepository.getByUserId(userId);
        if (favorites == null || favorites.size() == 0)
            return new ArrayList<>();

        List<TaleDto> favoriteTales = new ArrayList<>();
        for (Favorite favorite : favorites)
            favoriteTales.add(taleDetailService.getTaleDetail(favorite.getTaleId()));

        return favoriteTales;
    }


}
