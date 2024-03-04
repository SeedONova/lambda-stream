package com.putubgs;

/**
 * Hello world!
 *
 */

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;


import java.util.ArrayList;
import com.putubgs.model.Outlet;
import java.time.LocalDate;

public class App 
{
    public static void main( String[] args )
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lambda-stream");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT COUNT(o) FROM Outlet o");
        long count = (long) query.getSingleResult();

        if(count == 0){
            List<Outlet> allOutlet = new ArrayList<>();
            allOutlet.add(new Outlet("Manggarai", LocalDate.of(2022, 1, 1), 1000, 100));
            allOutlet.add(new Outlet("BSD", LocalDate.of(2022, 1, 1), 750, 19));
            allOutlet.add(new Outlet("Manggarai", LocalDate.of(2022, 1, 12), 1025, 32));
            allOutlet.add(new Outlet("Ragunan", LocalDate.of(2022, 1, 1), 375, 12));
            allOutlet.add(new Outlet("Ragunan", LocalDate.of(2022, 1, 2), 397, 13));
            allOutlet.add(new Outlet("BSD", LocalDate.of(2022, 1, 3), 803, 20));
            allOutlet.add(new Outlet("Pasar Minggu", LocalDate.of(2022, 1, 1), 35, 3));
            allOutlet.add(new Outlet("Pasar Minggu", LocalDate.of(2022, 1, 12), 5, 1));
            allOutlet.add(new Outlet("Manggarai", LocalDate.of(2022, 1, 15), 1005, 31));
            em.getTransaction().begin();
            for(Outlet outlet : allOutlet){
                em.persist(outlet); 
            }
            em.getTransaction().commit();
            em.close();  
        }

        

    }
}
