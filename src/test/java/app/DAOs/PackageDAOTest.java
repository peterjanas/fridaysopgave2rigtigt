package app.DAOs;

import app.entities.Package;
import app.entities.Package.DeliveryStatus;
import app.persistence.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PackageDAOTest {


    private static EntityManagerFactory emfTest;
    private static PackageDAO packageDAO;
   // private static LocationDAO locationDAO;
   // private static ShipmentDAO shipmentDAO;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTestMode(true);
        emfTest = HibernateConfig.getEntityManagerFactoryForTest();
        packageDAO = new PackageDAO(emfTest);
    //    locationDAO = new LocationDAO(emfTest);
      //  shipmentDAO = new ShipmentDAO(emfTest);

    }


    @AfterAll
    public static void tearDown() {
        emfTest.close();
    }

    @Test
    public void testCreatePackage() {
        // Create and persist a new package
        String trackingNumber = "ABC123";
        Package pkg = new Package();
        pkg.setTrackingNumber(trackingNumber);
        pkg.setSenderName("John Doe");
        pkg.setReceiverName("Jane Doe");
        pkg.setDeliveryStatus(DeliveryStatus.PENDING);

        packageDAO.persistPackage(pkg);

        // Retrieve and verify the package
        Package retrievedPackage = packageDAO.getPackageByTrackingNumber(trackingNumber);

        assertNotNull(retrievedPackage);
        assertEquals(trackingNumber, retrievedPackage.getTrackingNumber());
        assertEquals("John Doe", retrievedPackage.getSenderName());
        assertEquals("Jane Doe", retrievedPackage.getReceiverName());
        assertEquals(DeliveryStatus.PENDING, retrievedPackage.getDeliveryStatus());
    }

    @Test
    public void testUpdateDeliveryStatus() {
        // Create and persist a new package
        String trackingNumber = "XYZ789";
        Package pkg = new Package();
        pkg.setTrackingNumber(trackingNumber);
        pkg.setSenderName("Alice");
        pkg.setReceiverName("Bob");
        pkg.setDeliveryStatus(DeliveryStatus.PENDING);

        packageDAO.persistPackage(pkg);

        // Update the delivery status
        packageDAO.updateDeliveryStatus(trackingNumber, DeliveryStatus.IN_TRANSIT);

        Package updatedPackage = packageDAO.getPackageByTrackingNumber(trackingNumber);

        assertNotNull(updatedPackage);
        assertEquals(DeliveryStatus.IN_TRANSIT, updatedPackage.getDeliveryStatus());
    }

    @Test
    public void testRemovePackage() {
        // Create and persist a new package
        Package pkg = new Package();
        pkg.setTrackingNumber("DELETE001");
        pkg.setSenderName("Charles");
        pkg.setReceiverName("David");
        pkg.setDeliveryStatus(DeliveryStatus.PENDING);

        packageDAO.persistPackage(pkg);

        // Remove the package
        packageDAO.removePackage("DELETE001");

        // Verify that the package has been removed
        Package removedPackage = packageDAO.getPackageByTrackingNumber("DELETE001");

        assertNull(removedPackage);
    }


}