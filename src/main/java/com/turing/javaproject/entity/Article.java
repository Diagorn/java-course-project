package com.turing.javaproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "article", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article extends AbstractEntity {
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_user")
    private User author;
    @Column(name = "title", length = 250, nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "likes_number")
    private Short likesNumber;
    @Column(name = "thumbnail", length = 50)
    private String thumbnailUrl;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdAt;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH, mappedBy = "article")
    private List<Comment> comments;
    @ManyToMany
    @JoinTable(
            name = "article_tag",
            inverseJoinColumns = @JoinColumn(name = "id_tag"),
            joinColumns = @JoinColumn(name = "id_article")
    )
    private List<Tag> tags;
}
