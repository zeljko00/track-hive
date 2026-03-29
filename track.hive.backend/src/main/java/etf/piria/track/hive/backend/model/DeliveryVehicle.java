package etf.piria.track.hive.backend.model;

import etf.piria.track.hive.backend.model.enums.VehicleStatus;
import etf.piria.track.hive.backend.model.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery_vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 10, max = 10)
    @Column(unique = true, nullable = false, columnDefinition = "CHAR(10)")
    private String licensePlate;

    @NotBlank
    @Size(max = 20)
    @Column(unique = true, nullable = false, length = 20)
    private String vin;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100, updatable = false)
    private String manufacturer;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100, updatable = false)
    private String model;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Integer year;

    @NotNull
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String color;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private VehicleType type;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double capacityKg;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double capacityM3;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Double loadKg = 0.0;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Double mileageKm;

    /** Manufacturer-rated fuel consumption in litres per 100 km. */
    @Positive
    @Column
    private Double fuelConsumptionL100km;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude",        column = @Column(name = "current_latitude")),
        @AttributeOverride(name = "longitude",       column = @Column(name = "current_longitude")),
        @AttributeOverride(name = "address.country", column = @Column(name = "current_country", length = 100)),
        @AttributeOverride(name = "address.city",    column = @Column(name = "current_city",    length = 100)),
        @AttributeOverride(name = "address.address", column = @Column(name = "current_address", length = 150))
    })
    private Location location;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    @OrderBy("startedAt ASC")
    private List<Delivery> deliveries = new ArrayList<>();

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("refueledAt ASC")
    private List<FuelLog> fuelLogs = new ArrayList<>();

    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
