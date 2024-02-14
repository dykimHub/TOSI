package com.ssafy.tosi.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChild is a Querydsl query type for Child
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChild extends EntityPathBase<Child> {

    private static final long serialVersionUID = 1421076414L;

    public static final QChild child = new QChild("child");

    public final NumberPath<Integer> childId = createNumber("childId", Integer.class);

    public final StringPath childName = createString("childName");

    public final NumberPath<Integer> gender = createNumber("gender", Integer.class);

    public final BooleanPath myBaby = createBoolean("myBaby");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QChild(String variable) {
        super(Child.class, forVariable(variable));
    }

    public QChild(Path<? extends Child> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChild(PathMetadata metadata) {
        super(Child.class, metadata);
    }

}

