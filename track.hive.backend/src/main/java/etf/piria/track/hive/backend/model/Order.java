package etf.piria.track.hive.backend.model;

import etf.piria.track.hive.backend.model.enums.OrderPriority;
import etf.piria.track.hive.backend.model.enums.OrderStatus;
import etf.piria.track.hive.backend.model.enums.PaymentStatus;

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
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderPriority priority;

    @NotBlank
    @Size(min = 20, max = 20)
    @Column(unique = true, nullable = false, columnDefinition = "CHAR(20)")
    private String trackingNumber;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100)
    private String description;
    
    @Size(max = 500)
    @Column(length = 500)
    private String note;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double weightKg;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double volumeM3;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Double cost;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double value;

    @NotNull
    @Column(nullable = false)
    private Boolean fragile;
    
    @NotNull
    @Column(nullable = false)
    private Boolean signatureRequired;

    private LocalDateTime estimatedDeliveryTime;

    @NotNull
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name",            column = @Column(name = "sender_name",    nullable = false, length = 100)),
        @AttributeOverride(name = "address.country", column = @Column(name = "sender_country", nullable = false, length = 100)),
        @AttributeOverride(name = "address.city",    column = @Column(name = "sender_city",    nullable = false, length = 100)),
        @AttributeOverride(name = "address.address", column = @Column(name = "sender_address", nullable = false, length = 150)),
        @AttributeOverride(name = "email",           column = @Column(name = "sender_email",   length = 100)),
        @AttributeOverride(name = "phone",           column = @Column(name = "sender_phone",   length = 20))
    })
    private ContactInfo sender;

    @NotNull
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name",            column = @Column(name = "recipient_name",    nullable = false, length = 100)),
        @AttributeOverride(name = "address.country", column = @Column(name = "recipient_country", nullable = false, length = 100)),
        @AttributeOverride(name = "address.city",    column = @Column(name = "recipient_city",    nullable = false, length = 100)),
        @AttributeOverride(name = "address.address", column = @Column(name = "recipient_address", nullable = false, length = 150)),
        @AttributeOverride(name = "email",           column = @Column(name = "recipient_email",   length = 100)),
        @AttributeOverride(name = "phone",           column = @Column(name = "recipient_phone",   length = 20))
    })
    private ContactInfo recipient;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("occurredAt ASC")
    private List<OrderEvent> history = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = true)
    private Delivery delivery;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "coordinate.latitude",  column = @Column(name = "static_latitude")),
        @AttributeOverride(name = "coordinate.longitude", column = @Column(name = "static_longitude")),
        @AttributeOverride(name = "address.country", column = @Column(name = "static_country", length = 100)),
        @AttributeOverride(name = "address.city",    column = @Column(name = "static_city",    length = 100)),
        @AttributeOverride(name = "address.address", column = @Column(name = "static_address", length = 150))
    })
    private Location staticLocation;

    @Embedded
    private Location nextHop;

    // @Transient
    // public Location resolveLocation() {
    //     if (status == OrderStatus.IN_TRANSIT
    //             && Delivery != null
    //             && Delivery.getVehicle() != null) {
    //         return Delivery.getVehicle().getCurrentLocation();
    //     }
    //     return staticLocation;
    // }
}
