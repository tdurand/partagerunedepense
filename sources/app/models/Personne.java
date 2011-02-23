package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

public class Personne extends Model {
	
	public String nom;
	public String email;
	public float coeff;
	public float montant_depense;
	
	public float solde;
	public float soldeInitial;
	
    public Groupe groupe;
	
	public Personne(String nom, String email, float coeff, float montantDepense) {
		super();
		this.nom = nom;
		this.email = email;
		this.coeff = coeff;
		this.montant_depense = montantDepense;
	}

	public Personne() {
		// TODO Auto-generated constructor stub
	}
	
	public void calculSolde(float montant_unitaire)
	{
		solde=montant_depense-montant_unitaire;
	}

	@Override
	public String toString() {
		return "Personne [nom=" + nom + ", email=" + email + ", coeff=" + coeff
				+ ", montant_depense=" + montant_depense + ", solde=" + solde
				+ "]";
	}

	
	
	
	
	
    
}
