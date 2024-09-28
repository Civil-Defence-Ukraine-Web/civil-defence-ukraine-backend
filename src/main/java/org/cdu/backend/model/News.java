package org.cdu.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Accessors(chain = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE news SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction(value = "is_deleted = FALSE")
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private LocalDateTime publicationDate = LocalDateTime.now();
    private String image;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NewsType type;
    @Column(nullable = false, columnDefinition = "tinyint")
    private boolean isDeleted = false;

    public static enum NewsType {
        NEWS,
        REPORT,
        EVENT
    }
}
