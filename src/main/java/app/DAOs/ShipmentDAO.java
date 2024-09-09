package app.DAOs;

import app.entities.Location;
import app.entities.Shipment;
import app.persistence.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShipmentDAO
{
    private EntityManagerFactory emf;

    public ShipmentDAO()
    {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    // for test
    public ShipmentDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public void createShipment(Shipment shipment)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(shipment);
            em.getTransaction().commit();
        }
    }

    public Shipment getShipmentById(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Shipment.class, id);
        }
    }


    public Set<Shipment> getAllShipments()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<Shipment> query = em.createQuery("SELECT s FROM Shipment s", Shipment.class);
            List<Shipment> shipmentList = query.getResultList();
            return shipmentList.stream().collect(Collectors.toSet());
        }
    }


    public void updateShipment(Shipment shipment)
    {

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(shipment);
            em.getTransaction().commit();
        }
    }

    public void deleteShipment(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Shipment shipment = getShipmentById(id);
            if (shipment != null)
            {
                em.remove(shipment);
            }
            em.getTransaction().commit();
        }
    }
}
