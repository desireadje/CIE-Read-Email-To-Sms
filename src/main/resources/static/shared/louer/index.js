

function allChambreByIdCategorie() {

	var idCategorie = $('#idCategorie').val();
	

	$.ajax({
		url : "http://localhost:8080/chambre/findChambreByIdCategorie/" + idCategorie,
		type : 'GET',
		dataType : 'json',
		success : function(json) {

			var jsonData = json;
			var champSelectChambre = document.getElementById("idChambre");

			// alert(JSON.stringify(jsonData.length));

			if (jsonData.length > 0) {
				var reponse = jsonData;

				champSelectChambre.innerHTML = null;
				champSelectChambre.options[0] = new Option(
						'Veuillez sélectionner une chambre', '');
				var optlistCpt = champSelectChambre.options.length;

				for ( var key in reponse) {
					var chambre = reponse[key];

					champSelectChambre.options[optlistCpt] = new Option(
							chambre.nom, chambre.idChambre);
					optlistCpt = optlistCpt + 1;
				}
			} else {
				// var champSelectQuartier =
				// document.getElementById("ID_QUARTIER_UPDATE");
				champSelectChambre.innerHTML = null;
				champSelectChambre.options[0] = new Option(
						'Veuillez sélectionner une chambre', '');
			}
		},
		error : function(data, status, er) {
			console.log(data);
			console.log(status);
			console.log(er);
		}
	});
}