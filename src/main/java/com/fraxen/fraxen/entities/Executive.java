package com.fraxen.fraxen.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
@Entity
@Table(name = "executives")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Executive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gender;
    private String title;
    private String experience;
    private String sectorFocus;
    private String location;

    @OneToMany(mappedBy = "executive", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private Set<ExecutiveHighlight> highlights = new LinkedHashSet<>();

    @OneToMany(mappedBy = "executive", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private Set<ExecutiveStrength> strengths = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Executive)) return false;
        return id != null && id.equals(((Executive) o).id);
    }

    @Override
    public int hashCode() {
        return 31; // stable hash for Hibernate sets
    }
}
