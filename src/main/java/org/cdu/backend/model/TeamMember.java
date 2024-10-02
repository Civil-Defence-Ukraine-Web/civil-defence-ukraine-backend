package org.cdu.backend.model;

import jakarta.persistence.*;
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
@SQLDelete(sql = "UPDATE team_members SET is_deleted = TRUE WHERE id = ?")
@SQLRestriction(value = "is_deleted = FALSE")
@Table(name = "team_members")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String position;
    private String description;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false, columnDefinition = "tinyint")
    private boolean isDeleted = false;
}
