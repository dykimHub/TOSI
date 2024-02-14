package com.ssafy.tosi.tale;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTale is a Querydsl query type for Tale
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTale extends EntityPathBase<Tale> {

    private static final long serialVersionUID = -1196140968L;

    public static final QTale tale = new QTale("tale");

    public final StringPath characters = createString("characters");

    public final StringPath content1 = createString("content1");

    public final StringPath content2 = createString("content2");

    public final StringPath content3 = createString("content3");

    public final StringPath content4 = createString("content4");

    public final StringPath images = createString("images");

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    public final NumberPath<Integer> taleId = createNumber("taleId", Integer.class);

    public final NumberPath<Integer> time = createNumber("time", Integer.class);

    public final StringPath title = createString("title");

    public QTale(String variable) {
        super(Tale.class, forVariable(variable));
    }

    public QTale(Path<? extends Tale> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTale(PathMetadata metadata) {
        super(Tale.class, metadata);
    }

}

