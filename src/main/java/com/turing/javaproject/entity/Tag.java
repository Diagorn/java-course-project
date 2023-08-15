package com.turing.javaproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tag", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tag extends AbstractEntity{
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Tag parent;
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, mappedBy = "parent")
    private List<Tag> children;
    @ManyToMany
    @JoinTable(
            name = "article_tag",
            inverseJoinColumns = @JoinColumn(name = "id_article"),
            joinColumns = @JoinColumn(name = "id_tag")
    )
    private List<Article> articles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Tag tag = (Tag) o;

        if (!Objects.equals(getId(), tag.getId())) return false;
        return name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
}
