package app.DAOs;

import app.entities.Location;
import app.persistence.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocationDAOTest
{

    private static EntityManagerFactory emfTest;
    private static LocationDAO locationDAO;

    @BeforeAll
    static void setUpAll()
    {
        HibernateConfig.setTestMode(true);
        emfTest = HibernateConfig.getEntityManagerFactoryForTest();
        locationDAO = new LocationDAO(emfTest);
    }

    @AfterAll
    public static void tearDown()
    {
        emfTest.close();
    }

    @Test
    void testcreateLocation()
    {
        Location location = new Location();
        location.setLatitude(55.6761);
        location.setLongitude(12.5683);
        location.setAddress("Copenhagen, Denmark");
        locationDAO.createLocation(location);

        Location retrieved = locationDAO.getLocationById(location.getId());

        assertNotNull(retrieved);
        assertEquals("Copenhagen, Denmark", retrieved.getAddress());
    }


    @Test
    void testUpdateLocation()
    {
        Location location = new Location();

        location.setLatitude(55.6761);
        location.setLongitude(12.5683);
        location.setAddress("Copenhagen, Denmark");
        locationDAO.createLocation(location);

        Location updatedLocation = locationDAO.getLocationById(location.getId());
        updatedLocation.setAddress("Virum 123");
        updatedLocation.setLatitude(57.0488);
        updatedLocation.setLongitude(9.9217);


        locationDAO.updateLocation(updatedLocation);


        Location updated = locationDAO.getLocationById(location.getId());
        assertEquals("Virum 123", updated.getAddress());
    }

    @Test
    void testdeleteLocation()
    {
        Location location = new Location(null, 55.6761, 12.5683, "Delete Address");
        locationDAO.createLocation(location);

        Long locationId = location.getId();

        locationDAO.deleteLocation(locationId);
        assertNull(locationDAO.getLocationById(locationId));
    }
}