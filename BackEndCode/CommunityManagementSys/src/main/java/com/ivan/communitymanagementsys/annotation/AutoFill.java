package com.ivan.communitymanagementsys.annotation;

import com.ivan.communitymanagementsys.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //需要公共字段自动填充的数据库操作类型，UPDATE，INSERT
    OperationType value();
}

