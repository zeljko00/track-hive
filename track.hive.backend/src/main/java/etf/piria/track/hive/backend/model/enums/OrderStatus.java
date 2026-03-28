package etf.piria.track.hive.backend.model.enums;

public enum OrderStatus {
    PENDING,     // created, not yet assigned to a delivery run
    LOADING,     // being loaded onto a vehicle
    IN_TRANSIT,  // on its way to the recipient
    DELIVERED,   // successfully handed to recipient
    CANCELLED,   // cancelled before delivery
    RETURNED     // delivery failed, package returned to sender
}
