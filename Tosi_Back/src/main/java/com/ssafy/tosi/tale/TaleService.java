package com.ssafy.tosi.tale;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
//final field에 대한 생성자 주입
public class TaleService {
    private final TaleRepository taleRepository;

    //전체 조회
    public List<Tale> selectAllTales() {
        return taleRepository.findAll();
    }

    //taleId 기반으로 하나만 조회
    public Tale selectOneTale(int taleId) {
        return taleRepository.findOne(taleId);
    }

    //이름으로 검색
    public List<Tale> selectByTitle(String title) {
        return taleRepository.findByTitle(title);
    }

    //인기순 정렬
    public List<Tale> selectAllTalesOrderByLikeCnt() {
        return taleRepository.orderByLikeCnt();
    }

    //이름순 정렬
    public List<Tale> selectAllTalesOrderByTitle() {
        return taleRepository.orderByTitle();
    }

    //랜덤 정렬
    public List<Tale> selectAllTalesOrderByRandom() {
        return taleRepository.orderByRandom();
    }
}
