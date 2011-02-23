package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import models.Personne;
import models.Transaction;

import java.util.*;
import java.lang.Math;

public class Groupe extends Model {
	
	public List<Personne> personnes; 
	
	public float matriceTransactions[][];
	
	public String devise;
	
	public List<Transaction> transactions;
	
	public float coutunitaire;
	
	 
	 public Groupe() {

		this.personnes = new ArrayList();
	}
	
	 public Groupe(int nbpers)
	 {
		 this.personnes = new ArrayList();
		 
		 for (int i = 0; i < nbpers; i++) {
			 
			 this.personnes.add(new Personne());
			
		}
		 
	 }



	public void addPersonne(Personne personne)
	 {
		 personnes.add(personne);
	 }
	
	public void addPersonne(String nom, String email, float coeff, float montantDepense)
	{
		Personne personne=new Personne(nom,email,coeff,montantDepense);
		personnes.add(personne);
	}
	
	public void solve()
	{
		transactions=new ArrayList<Transaction>();
		
		Transaction transactionTampon;
	
		
		initSoldes();
		
		int i=0;
		
		while(sumAbsSolde()>0.1)
		{
			
			calculMatrice();
			transactionTampon=rechercherTransaction();			
			transactionTampon.effectuerTransaction();
			
			i++;
	
		}
		
		coutunitaire=montantUnitaire();
		
		
	}
	public void arrondirGroupe()
	{
		
		for (Iterator iterator = transactions.iterator(); iterator.hasNext();) {
			Transaction transaction = (Transaction) iterator.next();
			
			transaction.roundTransaction();
			
		}
		
	}
	
	
	public float sumDepense()
	{
		float total = 0;
		
		for (Iterator iterator = personnes.iterator(); iterator.hasNext();) {
			Personne personne = (Personne) iterator.next();
			
			total+=personne.montant_depense;
		}
		
		return total;
	}
	
	public void initSoldes()
	{
			
		for (Iterator iterator = personnes.iterator(); iterator.hasNext();) 
		{
			Personne personne = (Personne) iterator.next();
			
			personne.solde=personne.montant_depense-(sumDepense()/sumCoeff())*personne.coeff;
			personne.soldeInitial=personne.montant_depense-(sumDepense()/sumCoeff())*personne.coeff;

			
		}
		
	}
	
	
	public float sumAbsSolde()
	{
		float total = 0;
		
		for (Iterator iterator = personnes.iterator(); iterator.hasNext();) {
			Personne personne = (Personne) iterator.next();
			
			total+=Math.abs(personne.solde);
		}
		
		return total;
	}
	
	public float sumCoeff()
	{
		float total = 0;
		
		for (Iterator iterator = personnes.iterator(); iterator.hasNext();) {
			Personne personne = (Personne) iterator.next();
			
			total+=personne.coeff;
		}
		
		return total;
	}
	
	public float montantUnitaire()
	{
		return (sumDepense()/sumCoeff());
		
	}
	
	public void calculMatrice()
	{
		matriceTransactions=new float[personnes.size()][personnes.size()];
		
		for (int i = 0; i < personnes.size(); i++) 
		{
			
			for (int j = 0; j < personnes.size(); j++) 
			{
				
				matriceTransactions[i][j]=personnes.get(i).solde+personnes.get(j).solde;
				
			}
			
		}
	}
	
	public Transaction rechercherTransaction()
	{
		boolean transactionTrouve=false;
		float temp=sumDepense();
		Transaction transactionTampon = null;
		
		
		//recherche de niveau1
		for (int i = 0; i < personnes.size(); i++) 
		{
			
			for (int j = 0; j < personnes.size(); j++) 
			{
				if(matriceTransactions[i][j]>=-0.01 
						&& matriceTransactions[i][j]<temp 
						&& i!=j 
						&& personnes.get(i).solde!=0 
						&& personnes.get(j).solde!=0 
						&& isSigneContraire(personnes.get(i).solde,personnes.get(j).solde))
				{
					
						transactionTrouve=true;
				        temp=matriceTransactions[i][j];
				        
				   		if(personnes.get(i).solde>0) 
						{
							transactionTampon=new Transaction(personnes.get(i),personnes.get(j),Math.abs(personnes.get(j).solde));
							
						
						}
						else
						{
							transactionTampon=new Transaction(personnes.get(j),personnes.get(i),Math.abs(personnes.get(i).solde));
							
							
						}			
					
					
				}
				
				
				
			}
			
		}
		
		//niveau2
		if(transactionTrouve==false)
		{
			for (int i = 0; i < personnes.size(); i++) 
			{
				
				for (int j = 0; j < personnes.size(); j++) 
				{
					if(Math.abs(matriceTransactions[i][j])>=0.01 
							&& Math.abs(matriceTransactions[i][j])<temp 
							&& i!=j 
							&& personnes.get(i).solde!=0 
							&& personnes.get(j).solde!=0 
							&& isSigneContraire(personnes.get(i).solde,personnes.get(j).solde))
					{
						transactionTrouve=true;
				        temp=matriceTransactions[i][j];
				        
				        if(personnes.get(i).solde>0) 
						{
							transactionTampon=new Transaction(personnes.get(i),personnes.get(j),Math.min(Math.abs(personnes.get(i).solde), Math.abs(personnes.get(j).solde)));
						
						}
						else
						{
							transactionTampon=new Transaction(personnes.get(j),personnes.get(i),Math.min(Math.abs(personnes.get(i).solde), Math.abs(personnes.get(j).solde)));
							
						}			
						
					}
					
				}
			}
		}
		
		transactions.add(transactionTampon);
		
		return transactionTampon;
			
		
	}
	
	
	
	
	public boolean isSigneContraire(float a,float b)
	{
		if((a<0 && b>0) || (a>0 && b<0))
		{
		return true;
		}
		else
		{
		return false;
		}

	}
	

	@Override
	public String toString() {
		return "Groupe [personnes=" + personnes + "]";
		
	}
	
	
	
	
    
}
