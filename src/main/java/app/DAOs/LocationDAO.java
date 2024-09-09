package app.DAOs;

import app.entities.Location;
import app.entities.Package;
import app.persistence.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import okhttp3.Address;

import java.util.List;
import java.util.Set;

public class LocationDAO
{

    private EntityManagerFactory emf;

    public LocationDAO()
    {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    // for test
    public LocationDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public void createLocation(Location location)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
        }
    }

    public Location getLocationById(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Location.class, id);
        }
    }


    public void updateLocation(Location location)
    {

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(location);
            em.getTransaction().commit();
        }
    }

    public void deleteLocation(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Location location = getLocationById(id);
            if (location != null)
            {
                em.remove(location);
            }
            em.getTransaction().commit();
        }
    }

}
