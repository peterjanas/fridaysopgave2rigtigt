package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Package pkg;

    @ManyToOne
    @JoinColumn(name = "source_location_id", nullable = false)
    @Setter
    private Location sourceLocation;

    @ManyToOne
    @JoinColumn(name = "destination_location_id", nullable = false)
    @Setter
    private Location destinationLocation;

    @Column(nullable = false)
    private LocalDateTime shipmentDateTime;

    @PrePersist
    protected void onCreate()
    {
        shipmentDateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate()
    {
        shipmentDateTime = LocalDateTime.now();
    }

    public Shipment(Package pkg, Location sourceLocation, Location destinationLocation)
    {
        this.pkg = pkg;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
    }
}
