package etf.piria.track.hive.backend.model;

import etf.piria.track.hive.backend.model.enums.OrderStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "delivery_id", nullable = false, updatable = false)
    private Delivery delivery;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private OrderStatus status;

    @Size(max = 500)
    @Column(length = 500, updatable = false)
    private String note;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    private void onCreate() {
        timestamp = LocalDateTime.now();
    }
}
