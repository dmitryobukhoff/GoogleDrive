package com.example.driveservice.model;

import com.example.driveservice.model.enums.ObjectType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "file_info")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageObject {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private long size;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private ObjectType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user_id;
}
