package etf.piria.track.hive.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "fuel_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false, updatable = false)
    private DeliveryVehicle vehicle;

    @NotNull
    @Positive
    @Column(nullable = false, updatable = false)
    private Double liters;

    @PositiveOrZero
    @Column(updatable = false)
    private Double cost;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, updatable = false)
    private Double mileageKm;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime refueledAt;

    @PrePersist
    private void onCreate() {
        refueledAt = LocalDateTime.now();
    }
}
