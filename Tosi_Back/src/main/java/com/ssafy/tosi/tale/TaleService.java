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

    public List<Tale> selectAllTales() {
        return taleRepository.findAll();
    }

    public Tale selectOneTale(int taleId) {
        return taleRepository.findOne(taleId);
    }

    public List<Tale> selectByTitle(String title) {
        return taleRepository.findByTitle(title);
    }

    public List<Tale> selectAllTalesOrderByRandom() {
        return taleRepository.orderByRandom();
    }
}
