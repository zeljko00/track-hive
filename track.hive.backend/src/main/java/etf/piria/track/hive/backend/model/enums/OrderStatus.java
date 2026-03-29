package etf.piria.track.hive.backend.model.enums;

public enum OrderStatus {
    CREATED,   // order created, not yet processed
    APPROVED,  // order processed, approved for delivery
    DECLINED,  // order processed, declined for delivery
    PENDING,   // waiting for delivery
    IN_TRANSIT,  // on its way to the recipient
    DELIVERED,   // successfully handed to recipient
    CANCELLED,   // cancelled before delivery
    RETURNED     // delivery failed, package returned to sender
}
