package com.example.driveservice.model.metamodel;

import com.example.driveservice.model.StorageObject;
import com.example.driveservice.model.User;
import com.example.driveservice.model.enums.ObjectType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(StorageObject.class)
public abstract class StorageObject_ {

    public static volatile SingularAttribute<StorageObject, String> name;
    public static volatile SingularAttribute<StorageObject, String> path;
    public static volatile SingularAttribute<StorageObject, String> extension;
    public static volatile SingularAttribute<StorageObject, Long> size;
    public static volatile SingularAttribute<StorageObject, ObjectType> type;
    public static volatile SetAttribute<StorageObject, User> user_id;

    public static final String NAME = "name";
    public static final String PATH = "path";
    public static final String EXTENSION = "extension";
    public static final String SIZE = "size";
    public static final String TYPE = "type";
    public static final String USER_ID = "user_id";
}
