package com.ssafy.tosi.customTale;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomTale is a Querydsl query type for CustomTale
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomTale extends EntityPathBase<CustomTale> {

    private static final long serialVersionUID = 1486972088L;

    public static final QCustomTale customTale = new QCustomTale("customTale");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> customTaleId = createNumber("customTaleId", Integer.class);

    public final BooleanPath opened = createBoolean("opened");

    public final StringPath thumbnail = createString("thumbnail");

    public final NumberPath<Integer> time = createNumber("time", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QCustomTale(String variable) {
        super(CustomTale.class, forVariable(variable));
    }

    public QCustomTale(Path<? extends CustomTale> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomTale(PathMetadata metadata) {
        super(CustomTale.class, metadata);
    }

}

