package com.forage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "AlerteParametre")
public class Alerte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idStatut1", nullable = false)
    private Statut statut1;

    @ManyToOne
    @JoinColumn(name = "idStatut2", nullable = false)
    private Statut statut2;
    
    @Column(name = "duree_debut")
    private long dureeDebut;
    @Column(name = "duree_fin")
    private Long dureeFin;
    @Column(name = "alerte")
    private String alerte;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;   
    }

    public Statut getStatut1() {
        return statut1;
    }

    public void setStatut1(Statut statut1) {
        this.statut1 = statut1;
    }

    public Statut getStatut2() {
        return statut2;
    }

    public void setStatut2(Statut statut2) {
        this.statut2 = statut2;
    }

    public long getDureeDebut() {
        return dureeDebut;
    }

    public void setDureeDebut(long dureeDebut) {
        this.dureeDebut = dureeDebut;
    }

    public Long getDureeFin() {
        return dureeFin;
    }

    public void setDureeFin(Long dureeFin) {
        this.dureeFin = dureeFin;
    }

    public String getAlerte() {
        return alerte;
    }

    public void setAlerte(String alerte) {
        this.alerte = alerte;
    }
    
}
