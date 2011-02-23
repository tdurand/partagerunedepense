function addTableRow(table) 
{ 
     var $tr = $(table).find('tr:last').clone(); 
     $tr.find('input').attr('id', function(){ 
          var parts = this.id.match(/(\D+)(\d+)$/); 
          return parts[1] + ++parts[2]; 
     }); 
     $tr.find('input').attr('name', function(){ 
         var parts = this.name.match(/(\D+)(\d+)$/); 
         return parts[1] + ++parts[2]; 
    }); 
     $(table).append($tr); 
     return true; 
}
function majValidation()
{
	$("#cmaForm").validator({lang: 'fr',position: 'bottom left'} );
}

$(document).ready(function(){

	var nbPersOld = 1;
	var nbPersCourant = 0;
	
	function majNbChamps()
	{
		if($('#nbPers').val()!=nbPersCourant)
		{
			
		
		
		if ($('#nbPers').val() >= 1) 
				{
					nbPersCourant=parseInt($('#nbPers').val());

					//si le nombre de personne est supérieur au nombre de personne précédent
					if($('#nbPers').val()>nbPersOld)
					{
						//on créé des champs jusqu'a la valeur val(), a partir de nbPersOld+1
					
						for (i=nbPersOld+1; i<=nbPersCourant; i++) 
						{
							//ajout d'une personne
							//$(".unePersonne:first").clone().appendTo('#groupePersonnes');
							addTableRow("#groupePersonnes");
						}

						
						
					}
					else
					{
						//on retire (nbPersOld-nbPersCourant) champs a partir de nbPersCourant+1
						for (i=nbPersCourant+1; i<=nbPersOld; i++) 
						{
							//retrait d'une personne
							$(".unePersonne:last").remove();
						}
						
						
					}
					
				}
				
				//on stocke la valeur courante du nombre de personne
				nbPersOld=nbPersCourant;
				
				majValidation();
		}
	}
	
	$(window).load(function () {

			
			majNbChamps()
			$('.devise').html($('#devise').val());
			
	});
	
	$('#devise').bind('keyup change mouseover mousemove mouseleave', function() {
			
			$('.devise').html($('#devise').val());
	
	});
	
	
	
	$('#nbPers').bind('keyup change mouseover mousemove mouseleave', function() {


		if($('#nbPers').val()!=nbPersCourant)
		{
			
		
			if ($('#nbPers').val() >= 1) 
				{
					nbPersCourant=parseInt($('#nbPers').val());

					//si le nombre de personne est supérieur au nombre de personne précédent
					if($('#nbPers').val()>nbPersOld)
					{
						//on créé des champs jusqu'a la valeur val(), a partir de nbPersOld+1
					
						for (i=nbPersOld+1; i<=nbPersCourant; i++) 
						{
							//ajout d'une personne
							//$(".unePersonne:first").clone().appendTo('#groupePersonnes');
							addTableRow("#groupePersonnes");
						}

						
						
					}
					else
					{
						//on retire (nbPersOld-nbPersCourant) champs a partir de nbPersCourant+1
						for (i=nbPersCourant+1; i<=nbPersOld; i++) 
						{
							//retrait d'une personne
							$(".unePersonne:last").remove();
						}
						
						
					}
					
				}
				
				//on stocke la valeur courante du nombre de personne
				nbPersOld=nbPersCourant;
				
				majValidation();
		}
	
	});
	
	
});