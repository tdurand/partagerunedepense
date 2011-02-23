package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Groupe;
import models.Personne;
import models.UAgentInfo;
import play.data.validation.Required;
import play.i18n.Lang;
import play.mvc.*;

public class Application extends Controller {

    public static void index() {

    	UAgentInfo detector=new UAgentInfo(request.headers.get("user-agent").toString(),request.headers.get("accept").toString());
    	
    	if(detector.detectSmartphone())
    	{
    		renderTemplate("Application/mobile.html");
    	}
    	else
    	{
    		render();
    	}

    }
    public static void form() {
    	
        render();
    }
    
    public static void m() {
    	
		renderTemplate("Application/mobile.html");
    }
    
    public static void contact() {
    	
        render();
    }
    
    public static void changeLanguage(String lang) {
    	
    	Lang.change(lang);
        Application.index();
    }

    
    public static void save(int nbPers,String devise) 
    {
    	List<String> nom=new ArrayList<String>();
    	List<String> email=new ArrayList<String>();
    	List<Float> montant_depense_float=new ArrayList<Float>();
    	List<Float> coeff_float=new ArrayList<Float>();
    	
    	int nbPersTrouve=0;
    	int j=1;
    	
    	while(nbPersTrouve != nbPers)
    	{
    		if(params.get("nom-pers-"+j)!=null)
    		{
    			nom.add(params.get("nom-pers-"+j));
    			email.add(params.get("email-pers-"+j));
    			montant_depense_float.add(Float.parseFloat(params.get("montant-pers-"+j)));
    			coeff_float.add(Float.parseFloat(params.get("coeff-pers-"+j)));
    			
    			nbPersTrouve++;
    		}
    		
    		j++;
    	}
    	
    	Groupe groupe=new Groupe();
    	
    	for (int i = 0; i < nom.size(); i++) 
    	{
			
    		groupe.addPersonne(nom.get(i), email.get(i), coeff_float.get(i), montant_depense_float.get(i));
    		
		}
    	
    	groupe.devise=devise;
    	
    	groupe.solve();
    	//groupe.arrondirGroupe(); ne fonctionne pas, revoir la fonction d'arrondi dans Transaction
    	 	
    	if(request.format.equals("json"))
    	{
            renderJSON(groupe);
    	}
        else
        {
        	render("Application/result.html", groupe); 
        }

    }

}
