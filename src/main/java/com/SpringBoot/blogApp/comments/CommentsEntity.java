package com.SpringBoot.blogApp.comments;

import com.SpringBoot.blogApp.articles.ArticleEntity;
import com.SpringBoot.blogApp.users.UsersEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity(name = "comments")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Nullable
    private String title;

    @NonNull
    private String body;

    @CreatedDate
    private Date createdAt;


    @ManyToOne
    @JoinColumn(name="articleId",nullable = false)
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name="authorId",nullable = false)
    private UsersEntity author;


}
