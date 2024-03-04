package com.putubgs.model;

import java.sql.Date;
import java.util.UUID;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "t_outlet")
public class Outlet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID outletId;

    @Column(name = "outlet_name")
    private String outletName;

    @Column
    private LocalDate period;

    @Column
    private Integer omset;

    @Column(name = "total_trx")
    private Integer totalTrx;

    public Outlet(){};

    public Outlet(String outletName, LocalDate period, Integer omset, Integer totalTrx){
        this.outletName = outletName;
        this.period = period;
        this.omset = omset;
        this.totalTrx = totalTrx;
    }

    public UUID getOutletId(){
        return this.outletId;
    }

    public String getOutletName(){
        return this.outletName;
    }

    public LocalDate getPeriod(){
        return this.period;
    }

    public Integer getOmset(){
        return this.omset;
    }

    public Integer getTotalTrx(){
        return this.totalTrx;
    }
}
