package app.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Package
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String trackingNumber;

    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false)
    private String receiverName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;


    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "pkg", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Shipment> shipments;

    @PrePersist
    protected void onCreate()
    {
        lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate()
    {
        lastUpdated = LocalDateTime.now();
    }

    public enum DeliveryStatus
    {
        PENDING,
        IN_TRANSIT,
        DELIVERED
    }

    public Package(String trackingNumber, String senderName, String receiverName, DeliveryStatus deliveryStatus)
    {
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.deliveryStatus = deliveryStatus;
    }
}

