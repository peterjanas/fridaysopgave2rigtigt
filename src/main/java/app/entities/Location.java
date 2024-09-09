package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private Double latitude;

    @Setter
    @Column(nullable = false)
    private Double longitude;

    @Setter
    @Column(nullable = false)
    private String address;

    public Location(Double latitude, Double longitude, String address)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
}
