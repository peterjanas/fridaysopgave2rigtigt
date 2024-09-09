package app.DAOs;

import app.entities.Location;
import app.entities.Package;
import app.entities.Shipment;
import app.persistence.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShipmentDAOTest
{
    private static EntityManagerFactory emfTest;
    private static ShipmentDAO shipmentDAO;
    private static PackageDAO packageDAO;
    private static LocationDAO locationDAO;

    @BeforeAll
    static void setUpAll()
    {
        HibernateConfig.setTestMode(true);
        emfTest = HibernateConfig.getEntityManagerFactoryForTest();
        shipmentDAO = new ShipmentDAO(emfTest);
        packageDAO = new PackageDAO(emfTest);
        locationDAO = new LocationDAO(emfTest);
    }


    @AfterAll
    public static void tearDown()
    {
        emfTest.close();
    }

    @Test
    void testcreateShipment()
    {
        Package pkg = new Package("TRACK123", "Alice", "Bob", Package.DeliveryStatus.PENDING);
        packageDAO.persistPackage(pkg);
        Location source = new Location(55.6761, 12.5683, "Copenhagen");
        locationDAO.createLocation(source);
        Location destination = new Location(56.1629, 10.2039, "Aarhus");
        locationDAO.createLocation(destination);
        Shipment shipment = new Shipment(pkg, source, destination);
        shipmentDAO.createShipment(shipment);


        Shipment retrieved = shipmentDAO.getShipmentById(1L);
        assertNotNull(retrieved);
        assertEquals("Copenhagen", retrieved.getSourceLocation().getAddress());
        assertEquals("Aarhus", retrieved.getDestinationLocation().getAddress());
    }

    @Test
    void testGetAllShipments()
    {
        Package pkg = new Package("TRACK321", "Alice", "Bob", Package.DeliveryStatus.PENDING);
        packageDAO.persistPackage(pkg);
        Location source = new Location(55.6761, 12.5683, "Copenhagen");
        locationDAO.createLocation(source);
        Location destination = new Location(56.1629, 10.2039, "Aarhus");
        locationDAO.createLocation(destination);
        Shipment shipment = new Shipment(pkg, source, destination);
        shipmentDAO.createShipment(shipment);

        Set<Shipment> shipments = shipmentDAO.getAllShipments();
        assertFalse(shipments.isEmpty());
    }


    @Test
    void testupdateShipment()
    {
        Package pkg = new Package("TRACK222", "Alice", "Bob", Package.DeliveryStatus.PENDING);
        packageDAO.persistPackage(pkg);
        Location source = new Location(55.6761, 12.5683, "Copenhagen");
        locationDAO.createLocation(source);
        Location destination = new Location(56.1629, 10.2039, "Aarhus");
        locationDAO.createLocation(destination);

        Shipment shipment = new Shipment(pkg, source, destination);
        shipmentDAO.createShipment(shipment);

        Location updatedDestination = shipment.getDestinationLocation();
        updatedDestination.setAddress("Aalborg");
        updatedDestination.setLatitude(57.0488);
        updatedDestination.setLongitude(9.9217);

        locationDAO.updateLocation(updatedDestination);
        shipmentDAO.updateShipment(shipment);

        Shipment updated = shipmentDAO.getShipmentById(shipment.getId());
        assertEquals("Aalborg", updated.getDestinationLocation().getAddress());

    }

    @Test
    void testdeleteShipment()
    {
        Shipment shipment = shipmentDAO.getAllShipments().stream().findFirst().orElse(null);
        Long shipmentId = shipment.getId();
        shipmentDAO.deleteShipment(shipmentId);
        assertNull(shipmentDAO.getShipmentById(shipmentId));
    }
}