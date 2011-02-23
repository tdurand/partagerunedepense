package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.*;

public class Transaction extends Model {
	
	private Personne beneficiaire;
	private Personne payeur;
	private float montant;
	
	public Transaction(Personne beneficiaire, Personne payeur, float montant) {
		super();
		this.beneficiaire = beneficiaire;
		this.payeur = payeur;
		this.montant = montant;

	}
	
	public static double floor(double a, int n) {
		double p = Math.pow(10.0, n);
		return Math.floor((a*p)+0.5) / p;
	}


	public void effectuerTransaction()
	{
		
		beneficiaire.solde-=montant;
		payeur.solde+=montant;
		
		
	}
	
	public void roundTransaction()
	{
		beneficiaire.solde=(float) floor((double) beneficiaire.solde, 2);
		beneficiaire.soldeInitial=(float) floor((double) beneficiaire.soldeInitial, 2);
		beneficiaire.montant_depense=(float) floor((double) beneficiaire.montant_depense, 2);
		
		payeur.solde=(float) floor((double) beneficiaire.solde, 2);
		payeur.soldeInitial=(float) floor((double) beneficiaire.soldeInitial, 2);
		payeur.montant_depense=(float) floor((double) beneficiaire.montant_depense, 2);
		
		montant=(float) floor((double) montant, 2);
		
		
	}


	public Personne getBeneficiaire() {
		return beneficiaire;
	}


	public void setBeneficiaire(Personne beneficiaire) {
		this.beneficiaire = beneficiaire;
	}


	public Personne getPayeur() {
		return payeur;
	}


	public void setPayeur(Personne payeur) {
		this.payeur = payeur;
	}


	public float getMontant() {
		return montant;
	}


	public void setMontant(float montant) {
		this.montant = montant;
	}

	@Override
	public String toString() {
		return "Transaction [beneficiaire=" + beneficiaire + ", payeur="
				+ payeur + ", montant=" + montant + "]";
	}
	
	
	
	
	
	
    
}
