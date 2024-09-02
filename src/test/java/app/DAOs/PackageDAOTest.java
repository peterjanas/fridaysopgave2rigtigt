package app.DAOs;

import app.DAOs.PackageDAO;
import app.DeliveryStatus;
import app.entities.Package;
import app.persistence.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PackageDAOTest
{

    private static EntityManagerFactory emfTest;
    private static PackageDAO packageDAO;

    @BeforeAll
    static void setUpAll()
    {
        emfTest = HibernateConfig.getEntityManagerFactory();
        packageDAO = new PackageDAO();
    }

    @AfterAll
    public static void tearDown()
    {
        emfTest.close();
    }

    @Test
    public void testCreatePackage()
    {
        //new package
        String trackingNumber = "ABC125";
        Package pkg = new Package();
        pkg.setTrackingNumber(trackingNumber);
        pkg.setSenderName("John Doe");
        pkg.setReceiverName("Jane Doe");
        pkg.setDeliveryStatus(DeliveryStatus.PENDING);

        packageDAO.persistPackage(pkg);

        //check
        Package retrievedPackage = packageDAO.getPackageByTrackingNumber(trackingNumber);

        //assert
        assertNotNull(retrievedPackage);
        assertEquals(trackingNumber, retrievedPackage.getTrackingNumber());
        assertEquals("John Doe", retrievedPackage.getSenderName());
        assertEquals("Jane Doe", retrievedPackage.getReceiverName());
        assertEquals(DeliveryStatus.PENDING, retrievedPackage.getDeliveryStatus());
    }

    @Test
    public void testUpdateDeliveryStatus()
    {
        //new package
        String trackingNumber = "XYZ787";
        Package pkg = new Package();
        pkg.setTrackingNumber(trackingNumber);
        pkg.setSenderName("Alice");
        pkg.setReceiverName("Bob");
        pkg.setDeliveryStatus(DeliveryStatus.PENDING);

        packageDAO.persistPackage(pkg);

        //update delivery status
        packageDAO.updateDeliveryStatus(trackingNumber, DeliveryStatus.IN_TRANSIT);

        Package updatedPackage = packageDAO.getPackageByTrackingNumber(trackingNumber);
        //check updated status
        assertNotNull(updatedPackage);
        assertEquals(DeliveryStatus.IN_TRANSIT, updatedPackage.getDeliveryStatus());
    }

    @Test
    public void testRemovePackage()
    {
        //new package
        Package pkg = new Package();
        pkg.setTrackingNumber("DELETE001");
        pkg.setSenderName("Charles");
        pkg.setReceiverName("David");
        pkg.setDeliveryStatus(DeliveryStatus.PENDING);

        packageDAO.persistPackage(pkg);

        // Remove package
        packageDAO.removePackage("DELETE001");

        //check if removed
        Exception exception = assertThrows(Exception.class, () ->
        {
            packageDAO.getPackageByTrackingNumber("DELETE001");
        });

        assertNotNull(exception);
    }
}