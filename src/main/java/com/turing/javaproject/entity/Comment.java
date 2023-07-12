package com.turing.javaproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends AbstractEntity {
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_article")
    private Article article;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_user")
    private User author;
    @Column(name = "content", length = 240, nullable = false)
    private String content;
    @Column(name = "likes_number")
    private Short likesNumber;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "number", nullable = false)
    private Long number;
}
