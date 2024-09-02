package app.entities;

import app.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Package
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @PrePersist
    @PreUpdate
    public void updateTimestamp()
    {
        lastUpdated = LocalDateTime.now();
    }
}

