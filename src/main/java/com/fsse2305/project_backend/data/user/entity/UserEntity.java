package com.fsse2305.project_backend.data.user.entity;

import com.fsse2305.project_backend.data.user.domainObject.FirebaseUserData;
import jakarta.persistence.*;

@Entity
@Table(name = "user", indexes = {
        @Index(name = "idx_firebase_uid", columnList = "firebase_uid")
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;
    @Column(name = "firebase_uid", unique = true)
    private String firebaseUid;
    private String email;

    public UserEntity() {
    }

    public UserEntity(FirebaseUserData firebaseUserData) {
        this.firebaseUid = firebaseUserData.getFirebaseUid();
        this.email = firebaseUserData.getEmail();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
