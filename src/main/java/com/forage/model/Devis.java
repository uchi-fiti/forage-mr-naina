package com.forage.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Devis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reference;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateDevis;

    @OneToMany(mappedBy = "devis", fetch = FetchType.EAGER)
    private List<DetailsDevis> detailsDevis;

    @ManyToOne
    @JoinColumn(name="refDemande", nullable = false)
    private Demande demande;

    public Demande getDemande() {
		return demande;
	}

	public void setDemande(Demande demande) {
		this.demande = demande;
	}

	@ManyToOne
    @JoinColumn(name="idType", nullable = false)
    private Type type;

    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateDevis() {
        return dateDevis;
    }

    public void setDateDevis(LocalDateTime dateDevis) {
        this.dateDevis = dateDevis;
    }

    public List<DetailsDevis> getDetailsDevis() {
        return detailsDevis;
    }

    public void setDetailsDevis(List<DetailsDevis> detailsDevis) {
        this.detailsDevis = detailsDevis;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
