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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;

import com.putubgs.model.Outlet;
import java.time.LocalDate;
import java.util.Map;

public class App 
{
    public static void main( String[] args )
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lambda-stream");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT COUNT(o) FROM Outlet o");
        long count = (long) query.getSingleResult();

        List<Outlet> allOutlet;
        if(count == 0){
            allOutlet = new ArrayList<>();
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
        } else{
            allOutlet = em.createQuery("SELECT o FROM Outlet o", Outlet.class).getResultList();
        }
        
        for(Outlet outlet: allOutlet){
            System.out.println(outlet);
        }

        System.out.println();

        // Omset > Highest and Lowest
        Optional<Outlet> maxOmset = allOutlet.stream().max(Comparator.comparingInt(Outlet::getOmset));
        Optional<Outlet> minOmset = allOutlet.stream().min(Comparator.comparingInt(Outlet::getOmset));
        maxOmset.ifPresent(m -> System.out.println("Max Omset: " + m));
        minOmset.ifPresent(m -> System.out.println("Min Omset: " + m));

        System.out.println();

        // Omset < 600
        List<Outlet> omsetLessThan600 = allOutlet.stream()
                .filter(d -> d.getOmset() < 600)
                .collect(Collectors.toList());
        System.out.println("Omset < 600: " + omsetLessThan600);

        System.out.println();

        // Smallest Total Transactions
        allOutlet.stream()
                .collect(Collectors.groupingBy(Outlet::getPeriod, Collectors.summingInt(Outlet::getTotalTrx)))
                .entrySet().stream().min(Map.Entry.comparingByValue())
                .ifPresent(e -> System.out.println("Date with smallest total transactions: " + e.getKey()));

        System.out.println();

        // Total Omset and Total Transactions per Outlet
        Map<String, IntSummaryStatistics> statsPerOutlet = allOutlet.stream()
                .collect(Collectors.groupingBy(Outlet::getOutletName, Collectors.summarizingInt(Outlet::getOmset)));
        statsPerOutlet.forEach((k, v) -> System.out.println("Outlet: " + k + " Total Omset: " + v.getSum() + ", Total Transactions: " + v.getCount()));

        System.out.println();

        // Total Omset and Total Transactions Overall
        IntSummaryStatistics totalStats = allOutlet.stream()
                .collect(Collectors.summarizingInt(Outlet::getOmset));
        System.out.println("Total Omset: " + totalStats.getSum() + ", Total Transactions: " + totalStats.getCount());
        
    }
}
