package com.SpringBoot.blogApp.users;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;


@Entity(name = "users")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String username;
    @Column(nullable = false)
    @NonNull
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    @Nullable
    private String bio;

    @Column(nullable = true)
    @Nullable
    private String image;


}
