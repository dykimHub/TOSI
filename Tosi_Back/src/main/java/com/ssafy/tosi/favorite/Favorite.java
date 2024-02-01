package com.ssafy.tosi.favorite;

import com.ssafy.tosi.tale.Tale;
import com.ssafy.tosi.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "favorites")
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer favoriteId;

    @Column(nullable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "taleId")
    private Tale tale;
}
