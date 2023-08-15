package com.turing.javaproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "article", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Article extends AbstractEntity {
    @ManyToOne(fetch = FetchType.EAGER)
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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "article")
    private List<Comment> comments;
    @ManyToMany
    @JoinTable(
            name = "article_tag",
            inverseJoinColumns = @JoinColumn(name = "id_tag"),
            joinColumns = @JoinColumn(name = "id_article")
    )
    private List<Tag> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Article article = (Article) o;

        if (!title.equals(article.title)) return false;
        if (!content.equals(article.content)) return false;
        if (!Objects.equals(likesNumber, article.likesNumber)) return false;
        if (!Objects.equals(thumbnailUrl, article.thumbnailUrl))
            return false;
        if (!Objects.equals(getId(), article.getId())) return false;
        return Objects.equals(createdAt, article.createdAt);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + (likesNumber != null ? likesNumber.hashCode() : 0);
        result = 31 * result + (thumbnailUrl != null ? thumbnailUrl.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
}
