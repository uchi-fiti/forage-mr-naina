package com.forage.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DemandeStatut")
public class DemandeStatut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "refDemande", nullable = false)
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "idStatut", nullable = false)
    private Statut statut;

    @Column(nullable = false)
    private LocalDateTime dateChangementStatut;

    @Column(name = "dureeTravaillee")
    private float dureeTravaillee;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateChangementStatut() {
        return dateChangementStatut;
    }

    public void setDateChangementStatut(LocalDateTime dateChangementStatut) {
        this.dateChangementStatut = dateChangementStatut;
    }

    public float getDureeTravaillee() {
        return dureeTravaillee;
    }

    public void setDureeTravaillee(float dureeTravaillee) {
        this.dureeTravaillee = dureeTravaillee;
    }






}
