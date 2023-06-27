package com.fsse2305.project_backend.service;

import com.fsse2305.project_backend.data.user.domainObject.FirebaseUserData;
import com.fsse2305.project_backend.data.user.entity.UserEntity;

public interface UserService {
    UserEntity getEntityByFirebaseUserData(FirebaseUserData firebaseUserData);
}
