package com.ssafy.tosi.favorite;

import com.ssafy.tosi.tale.Tale;
import com.ssafy.tosi.tale.TaleDto;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
public class FavoriteDto {

    private Integer favoriteId;

    private Integer userId;

    private TaleDto taleDto;

}
