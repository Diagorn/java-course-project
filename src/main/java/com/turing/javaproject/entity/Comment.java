package com.turing.javaproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comment", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (!content.equals(comment.content)) return false;
        if (!Objects.equals(likesNumber, comment.likesNumber)) return false;
        if (!Objects.equals(createdAt, comment.createdAt)) return false;
        if (!Objects.equals(getId(), comment.getId())) return false;
        return Objects.equals(number, comment.number);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + (likesNumber != null ? likesNumber.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
}
