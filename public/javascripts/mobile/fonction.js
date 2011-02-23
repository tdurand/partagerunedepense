/**
 * @author Thibault
 */

//overide default jmobile settings
$(document).bind("mobileinit", function(){
  $.mobile.ajaxFormsEnabled = false;
});

$(document).ready(function() { 
	
		
	
		//Global Submit handler
		$('#formPrincipal').ajaxForm({    
			// dataType identifies the expected content type of the server response 
		    dataType:'json', 
		
		    // success identifies the function to invoke when the server response 
		    // has been received 
		    beforeSerialize: function($form, options) 
		    { 
		    	if($("#nbPers").val()==0)
		    	{
		    		$("#messErrorParticipants").text(messErrorParticipants);
		    		
		    		return false;
		    	}
		    	
		    	$.mobile.pageLoading();	             
		    }
			,
		    success: function(data) 
		    {
		    	var ligneTransaction;
		    	var lignePersonne;

		    	//reinitialise la page
		    	$("#resultTransactions").html('');
		    	$(".lignePersonne").remove();
		    		
		    	
		    	//parser le resultat
		    		//devise
			    	$("#resultDevise").text(data.devise);
			    	//cout unitaire
			    	$("#resultCoutUnitaire").text(data.coutunitaire);
			    	//transactions
			    	 $.each(data.transactions, function(i,item){
	
			    		 ligneTransaction='<li><strong>'+item.payeur.nom+'</strong> '+messDoit+' <strong>'+item.montant+' '+data.devise+'</strong> '+messA+' <strong>'+ item.beneficiaire.nom +'</strong></li>';
			    		 
			    		 $("#resultTransactions").append(ligneTransaction);
			    		 
			    		 
			           });
			    	 
			    	 //tableau personnes
			    	 $.each(data.personnes, function(i,item)
			    		{
			    			
			    		 lignePersonne='<tr class="lignePersonne">'+
			    		    '<td>'+item.nom+'</td>' +
			    		    //'<td>'+item.email+'</td>' + 
			    		    '<td>'+item.montant_depense+' '+data.devise+'</td>' + 
			    		    //'<td>'+item.coeff+'</td>' + 
			    		    //'<td>'+item.solde+' '+data.devise+'</td>' +
			    		  '</tr>';
			    		 
			    		 $("#resultPersonnes").append(lignePersonne);
			    		 
			    		 
			           });
		    	
		    	
		    	$.mobile.changePage("#resultat","fade");
			} 
			
		}); 
		
	


});

var modification=false;
var numPers=0;
var nbPers=0;


//fonction qui incremente le num de la personne
function addListRow(list) 
{ 
	 var numPersEnCours;
     var $tr = $(list).find('li:last').clone(); 
     $tr.find('input').attr('id', function(){ 
          var parts = this.id.match(/(\D+)(\d+)$/); 
          numPersEnCours=parts[2];
          return parts[1] + ++parts[2]; 
     }); 
     $tr.find('input').attr('name', function(){ 
         var parts = this.name.match(/(\D+)(\d+)$/); 
         return parts[1] + ++parts[2]; 
    }); 
     numPersEnCours++
     $tr.attr("id",numPersEnCours);
     
     $(list).append($tr); 
     return true; 
}

function ajoutPersonne()
{
	/* Ajout d'une personne  */
	
	//si le nombre de personne est superieur a zero
	if($("#listePersonnes > li").length > 0)
		{
			//on clone la ligne precedente en changeant les id 
			addListRow("#listePersonnes");
		}
	else
		{
			//on clone la ligne modele 
			$("#listePersonnes").append($("#lignePersonneModele").clone().attr("id","1"));
			$("#listePersonnes").listview('refresh');
		}
		
	//on recup lid en cours
	$li=$("#listePersonnes").find('li:last');
	
	//on copie le nom dans la liste
	$li.find('a[id="nom-pers"]').text($('input[id="nom"]').val());
	
	//copie des infos en hidden
	$li.find('input[id|="nom-pers"]').val($('input[id="nom"]').val());
	$li.find('input[id|="montant-pers"]').val($('input[id="montant"]').val());
	$li.find('input[id|="coeff-pers"]').val($('input[id="coeff"]').val());
	$li.find('input[id|="email-pers"]').val($('input[id="email"]').val());
	
	

}

function modifPersonne()
{
	/* Modification d'une personne */
	
	//recup ligne personne
	$li=$('li[id="'+numPers+'"]');
	
	//on copie le nom dans la liste
	$li.find('a[id="nom-pers"]').text($('input[id="nom"]').val());
	
	//copie des infos en hidden
	$li.find('input[id|="nom-pers"]').val($('input[id="nom"]').val());
	$li.find('input[id|="montant-pers"]').val($('input[id="montant"]').val());
	$li.find('input[id|="coeff-pers"]').val($('input[id="coeff"]').val());
	$li.find('input[id|="email-pers"]').val($('input[id="email"]').val());


}


//Handler OK dialog ajout/modif personne
$('#btnSubmitPersonne').live('click tap', function(event) 
{
	//validation du formulaire
	if($("#nom").val()=='')
		{
			$("#messErrorNom").text(messErrorNom);
			
			return false;
		}
	
	//si c'est une modification
	if(modification)
	{
		modifPersonne()
		modification=false;
	}
	//sinon c'est un ajout
	else
	{
		ajoutPersonne();
		nbPers=$("#nbPers").val();
		nbPers++;
		$("#nbPers").val(nbPers);
		
	}
	
	$.mobile.changePage("#form","fade");

 
});
//Handler suppression personne
$('#btnSupprPersonne').live('click tap', function(event) 
{
		    
	//supprime la personne de la liste
	$li=$(this).closest('li');
	$li.remove();
	
	nbPers=$("#nbPers").val();
	nbPers--;
	$("#nbPers").val(nbPers);
	
});

//Handler Modification personne
$('#nom-pers').live('click tap', function(event) 
{
	//li
	$li=$(this).closest('li');
	
	//on recupere le numero de la personne
	numPers=$li.attr('id');
	
	//on positionne modification à true
	modification=true;
	
	//on prerempli le formulaire avec les données
	$('input[id="nom"]').val($li.find('input[id|="nom-pers"]').val());
	$('input[id="montant"]').val($li.find('input[id|="montant-pers"]').val());
	$('input[id="coeff"]').val($li.find('input[id|="coeff-pers"]').val());
	$('input[id="email"]').val($li.find('input[id|="email-pers"]').val());
	
	$('#deviseLabel').text($('#devise').val());
	
	$('.messError').text('');
	

	
	
	
});

//Handler btnAjoutPersonne
$('#btnAjoutPersonne').live('click tap', function(event) 
{

	//reset du form
	$('#formPersonne')[0].reset(); 
	
	$('#deviseLabel').text($('#devise').val());
	
	$('.messError').text('');
	
	
});

$('#btnCommencer').live('click tap', function(event) 
{

	//reset de la liste
	$("#listePersonnes").find('li').remove();
	
	$("#nbPers").val(0);
			
	
});











