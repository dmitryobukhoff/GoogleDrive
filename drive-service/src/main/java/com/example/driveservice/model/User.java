package com.example.driveservice.model;

import com.example.driveservice.model.metamodel.StorageObject_;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_info", schema = "google_drive")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = StorageObject_.USER_ID)
    private Set<StorageObject> objects;

}
