package com.ssafy.tosi.customTale;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomTaleService {

    private final CustomTaleRepository customTaleRepository;

    @Autowired
    public CustomTaleService(CustomTaleRepository customTaleRepository) {
        this.customTaleRepository = customTaleRepository;
    }


    //customTaleId에 해당하는 CustomTale 엔터티 (커스텀 동화 상세조회)
    public Optional<CustomTale> getCustomTale(Long customTaleId) {
        return customTaleRepository.findById(customTaleId);
    }

    //userId에 해당하는 CustomTale 엔터티를 조회 (나의 책장 - 내가 만든 동화)
    public List<CustomTale> getCustomTalesByUserId(Long userId) {
        // Implement your custom query method in the repository if needed
        return customTaleRepository.findByUserId(userId);
    }

    //isPublic이 true인 CustomTale 엔터티를 조회 (친구들이 만든 동화보기 목록)
    public List<CustomTale> getCustomTales() {
        return customTaleRepository.findByIsPublic(true);
    }

    //CustomTale 엔터티를 저장 (내가 만드는 동화 저장)
    public CustomTale postCustomTale(CustomTale customTale) {
        return customTaleRepository.save(customTale);
    }

    //customTaleId에 해당하는 CustomTale 엔터티의 isPublic 값 변경 (나의 책장 - 내가 만든 동화)
    public CustomTale putCustomTale(Long customTaleId, boolean isPublic) {
        Optional<CustomTale> optionalCustomTale = customTaleRepository.findById(customTaleId);

        if (optionalCustomTale.isPresent()) {
            CustomTale customTale = optionalCustomTale.get();
            customTale.setPublic(isPublic); //JavaBeans 규악에 따라 setIsPublic가 아니라 setPublic라는 세터 메소드를 생성합니다.
            return customTaleRepository.save(customTale);
        } else {
            return null;
        }
    }

    //customTaleId에 해당하는 CustomTale 엔터티를 삭제
    public boolean deleteCustomTale(Long customTaleId) {
        customTaleRepository.deleteById(customTaleId);
        return true;
    }

}
