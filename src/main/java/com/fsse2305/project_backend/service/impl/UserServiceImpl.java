package com.fsse2305.project_backend.service.impl;

import com.fsse2305.project_backend.data.user.domainObject.FirebaseUserData;
import com.fsse2305.project_backend.data.user.entity.UserEntity;
import com.fsse2305.project_backend.repository.UserRepository;
import com.fsse2305.project_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getEntityByFirebaseUserData(FirebaseUserData firebaseUserData) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByFirebaseUid(firebaseUserData.getFirebaseUid());
        if (optionalUserEntity.isEmpty()) {
            UserEntity newUserEntity = new UserEntity(firebaseUserData);
           newUserEntity =  userRepository.save(newUserEntity);
            return newUserEntity;

//            return userRepository.save(new UserEntity(firebaseUserData));
        }
        return optionalUserEntity.get();
    }
}
