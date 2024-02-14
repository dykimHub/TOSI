package com.ssafy.tosi.favorite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFavorite is a Querydsl query type for Favorite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavorite extends EntityPathBase<Favorite> {

    private static final long serialVersionUID = 1754672408L;

    public static final QFavorite favorite = new QFavorite("favorite");

    public final NumberPath<Integer> favoriteId = createNumber("favoriteId", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> taleId = createNumber("taleId", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QFavorite(String variable) {
        super(Favorite.class, forVariable(variable));
    }

    public QFavorite(Path<? extends Favorite> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFavorite(PathMetadata metadata) {
        super(Favorite.class, metadata);
    }

}

