package org.cdu.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

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
    private LocalDateTime date = LocalDateTime.now();
    private String image;
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