package app.DAOs;

import app.entities.DeliveryStatus;
import app.entities.Package;
import app.persistence.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class PackageDAO
{
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    // Default constructor for normal use
    public PackageDAO() {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    // Constructor for testing
    public PackageDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void persistPackage(Package pkg)
    {

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(pkg);
            em.getTransaction().commit();
        }
    }

    public Package getPackageByTrackingNumber(String trackingNumber)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<Package> query = em.createQuery(
                    "SELECT p FROM Package p WHERE p.trackingNumber = :trackingNumber", Package.class);
            query.setParameter("trackingNumber", trackingNumber);
            return query.getSingleResult();
        } catch (NoResultException e)
        {
            return null;
        }
    }

    public void updateDeliveryStatus(String trackingNumber, DeliveryStatus status)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Package pkg = getPackageByTrackingNumber(trackingNumber);
            pkg.setDeliveryStatus(status);
            em.merge(pkg);
            em.getTransaction().commit();
        }
    }

    public void removePackage(String trackingNumber)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(getPackageByTrackingNumber(trackingNumber));
            em.getTransaction().commit();
        }
    }
}
