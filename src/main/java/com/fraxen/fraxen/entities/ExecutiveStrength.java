package com.fraxen.fraxen.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
@Entity
@Table(name = "executive_strengths")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExecutiveStrength {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executive_id", nullable = false)
    @JsonIgnore
    private Executive executive;

    private String strengthDescription;
    private Integer displayOrder = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExecutiveStrength)) return false;
        return id != null && id.equals(((ExecutiveStrength) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
