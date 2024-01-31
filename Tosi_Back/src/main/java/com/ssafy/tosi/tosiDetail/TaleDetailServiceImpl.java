package com.ssafy.tosi.tosiDetail;

import com.ssafy.tosi.tale.Tale;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaleDetailServiceImpl implements TaleDetailService {

    private final TaleDetailRepository taleDetailRepository;

    public Tale getTaleDetail(int taleId) {
        return taleDetailRepository.findById(taleId).orElseThrow(() -> new EntityNotFoundException());

    }
}
