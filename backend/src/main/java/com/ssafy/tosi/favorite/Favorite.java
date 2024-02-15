package com.ssafy.tosi.favorite;

import com.ssafy.tosi.tale.Tale;
import com.ssafy.tosi.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "favorites")
@Data
@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer favoriteId;

    @Column(nullable = false)
    private Integer userId;

    @Column
    private int taleId;

    @Column(insertable = false)
    private LocalDateTime regDate;

//    @ManyToOne
//    @JoinColumn(name = "taleId")
//    private Tale tale;
}
