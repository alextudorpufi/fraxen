package com.fraxen.fraxen.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Table(name = "executive_highlights")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExecutiveHighlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executive_id", nullable = false)
    @JsonIgnore
    private Executive executive;

    private String positionTitle;
    private String companyDescription;
    private String details;
    private Integer displayOrder = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExecutiveHighlight)) return false;
        return id != null && id.equals(((ExecutiveHighlight) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
