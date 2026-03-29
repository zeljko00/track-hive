package etf.piria.track.hive.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import etf.piria.track.hive.backend.model.Coordinate;

@Entity
@Table(name = "regions")
@Getter
@Setter
@NoArgsConstructor
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @ElementCollection
    @CollectionTable(name = "region_boundary_coordinates", joinColumns = @JoinColumn(name = "region_id"))
    @OrderColumn(name = "coordinates_order")
    private List<Coordinate> boundary = new ArrayList<>();
}
