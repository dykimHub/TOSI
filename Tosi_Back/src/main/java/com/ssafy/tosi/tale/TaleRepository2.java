package com.ssafy.tosi.tale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;

public interface TaleRepository2 extends JpaRepository<Tale, Integer>, QuerydslPredicateExecutor<Tale>, // 엔티티 안에 있는 모든 필드에 대한 기본 검색기능을 추가해준다. 대소문자를 구분하지는 않는다. 하지만 부분검색이 안된다.
        QuerydslBinderCustomizer {

}
