
let ligneCounter = 0;
let demandeRecherchee = null;

document.addEventListener('DOMContentLoaded', function() {
    initialiserEvenements();
    ajouterLigne();
    // chargerTypesDevis();
});

function initialiserEvenements() {
    const demandeInput = document.getElementById('demandeId');
    const ajouterBtn = document.getElementById('ajouterLigne');
    const devisForm = document.getElementById('devisForm');
    
    demandeInput.addEventListener('blur', rechercherDemande);
    demandeInput.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            rechercherDemande();
        }
    });
    
    ajouterBtn.addEventListener('click', ajouterLigne);
    devisForm.addEventListener('submit', validerDevis);
}

function chargerTypesDevis() {
    const xhr = new XMLHttpRequest();
    const url = '/devis/types-devis';
    
    xhr.open('GET', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            try {
                const types = JSON.parse(xhr.responseText);
                const select = document.getElementById('devisType');
                
                select.innerHTML = '<option value="">Sélectionnez un type</option>';
                
                types.forEach(function(type) {
                    const option = document.createElement('option');
                    option.value = type.id;
                    option.textContent = type.nom;
                    select.appendChild(option);
                });
            } catch (e) {
                console.error('Erreur lors du chargement des types:', e);
            }
        }
    };
    
    xhr.send();
}

function rechercherDemande() {
    const id = document.getElementById('demandeId').value.trim();
    const resultatsDiv = document.getElementById('resultats');
    
    if (id === '') {
        resultatsDiv.innerHTML = '<div style="color: orange;">Veuillez saisir un ID</div>';
        return;
    }
    
    resultatsDiv.innerHTML = '<div>Recherche en cours...</div>';
    
    const xhr = new XMLHttpRequest();
    const url = '/devis/recherche-demande?id=' + encodeURIComponent(id);
    
    xhr.open('GET', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    const demande = JSON.parse(xhr.responseText);
                    demandeRecherchee = demande;
                    afficherResultat(demande, resultatsDiv);
                } catch (e) {
                    console.error('Erreur:', e);
                    resultatsDiv.innerHTML = '<div style="color: red;">Erreur lors du traitement des données</div>';
                }
            } else if (xhr.status === 404) {
                demandeRecherchee = null;
                resultatsDiv.innerHTML = '<div style="color: red;">Demande non trouvée</div>';
            } else {
                resultatsDiv.innerHTML = '<div style="color: red;">Erreur lors de la recherche: ' + xhr.status + '</div>';
            }
        }
    };
    
    xhr.onerror = function() {
        resultatsDiv.innerHTML = '<div style="color: red;">Erreur de connexion au serveur</div>';
    };
    
    xhr.send();
}

// function afficherResultat(demande, resultatsDiv) {
//     if (!demande) {
//         resultatsDiv.innerHTML = '<div>Demande non trouvée</div>';
//         return;
//     }
    
//     let html = '<div><strong>Demande trouvée :</strong></div>';
//     html += '<div style="margin: 10px 0; padding: 10px; border: 1px solid #ccc;">';
//     html += '<div>ID: ' + (demande.id || 'N/A') + '</div>';
//     html += '<div>Date: ' + (demande.date || 'N/A') + '</div>';
//     html += '<div>Lieu: ' + (demande.lieu || 'N/A') + '</div>';
//     html += '<div>District: ' + (demande.district || 'N/A') + '</div>';
//     html += '<div>Client: ' + (demande.clientNom || 'N/A') + '</div>';
//     html += '<div>Statut: ' + (demande.statutActuel || 'N/A') + '</div>';
//     html += '</div>';
    
//     resultatsDiv.innerHTML = html;
// }

function afficherResultat(demande, resultatsDiv) {
    if (!demande) {
        resultatsDiv.innerHTML = '<div class="error-message">Demande non trouvée</div>';
        return;
    }
    
    // Fonction pour formater la date
    function formatDate(dateString) {
        if (!dateString) return 'N/A';
        let date = new Date(dateString);
        if (isNaN(date.getTime())) return dateString;
        
        let jour = date.getDate().toString().padStart(2, '0');
        let mois = (date.getMonth() + 1).toString().padStart(2, '0');
        let annee = date.getFullYear().toString().slice(-2);
        let heures = date.getHours().toString().padStart(2, '0');
        let minutes = date.getMinutes().toString().padStart(2, '0');
        
        return `${jour}/${mois}/${annee} ${heures}:${minutes}`;
    }
    
    let html = `
        <div class="resultat-card">
            <div class="resultat-title">
                 Demande trouvée
            </div>
            <div class="info-grid">
                <div class="info-item">
                    <span class="info-label">ID</span>
                    <span class="info-value">${demande.id || 'N/A'}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">Date</span>
                    <span class="info-value">${formatDate(demande.date)}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">Lieu</span>
                    <span class="info-value">${demande.lieu || 'N/A'}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">District</span>
                    <span class="info-value">${demande.district || 'N/A'}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">Client</span>
                    <span class="info-value">${demande.clientNom || 'N/A'}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">Statut</span>
                    <span class="info-value">${demande.statutActuel || 'Aucun'}</span>
                </div>
            </div>
        </div>
    `;
    
    resultatsDiv.innerHTML = html;
}

function ajouterLigne() {
    const tbody = document.getElementById('tableBody');
    const ligneId = ligneCounter++;
    
    const tr = document.createElement('tr');
    tr.id = 'ligne_' + ligneId;
    
    tr.innerHTML = `
        <td><input type="text" name="libelle" class="libelle" data-id="${ligneId}" required></td>
        <td><input type="number" name="pu" class="pu" data-id="${ligneId}" step="0.01" value="0" required></td>
        <td><input type="number" name="qte" class="qte" data-id="${ligneId}" step="0.01" value="0" required></td>
        <td><span class="montant" data-id="${ligneId}">0.00</span></td>
        <td><button type="button" class="supprimerLigne" data-id="${ligneId}"> X </button></td>
    `;
    
    tbody.appendChild(tr);
    
    const puInput = tr.querySelector('.pu');
    const qteInput = tr.querySelector('.qte');
    
    puInput.addEventListener('input', function() { calculerMontant(ligneId); });
    qteInput.addEventListener('input', function() { calculerMontant(ligneId); });
    
    const supprimerBtn = tr.querySelector('.supprimerLigne');
    supprimerBtn.addEventListener('click', function() { supprimerLigne(ligneId); });
}

function calculerMontant(ligneId) {
    const pu = parseFloat(document.querySelector(`.pu[data-id="${ligneId}"]`).value) || 0;
    const qte = parseFloat(document.querySelector(`.qte[data-id="${ligneId}"]`).value) || 0;
    const montant = pu * qte;
    
    const montantSpan = document.querySelector(`.montant[data-id="${ligneId}"]`);
    montantSpan.textContent = montant.toFixed(2);
}

function supprimerLigne(ligneId) {
    const ligne = document.getElementById(`ligne_${ligneId}`);
    if (ligne) {
        ligne.remove();
    }
}

function validerDevis(event) {
    event.preventDefault();
    
    if (!demandeRecherchee) {
        alert('Veuillez d\'abord rechercher et sélectionner une demande valide');
        return;
    }
    
    const devisTypeId = document.getElementById('devisType').value;
    if (!devisTypeId) {
        alert('Veuillez sélectionner un type de devis');
        return;
    }
    
    const details = [];
    const lignes = document.querySelectorAll('#tableBody tr');
    
    for (let ligne of lignes) {
        const libelle = ligne.querySelector('.libelle').value;
        const pu = parseFloat(ligne.querySelector('.pu').value);
        const qte = parseFloat(ligne.querySelector('.qte').value);
        
        if (libelle && pu > 0 && qte > 0) {
            details.push({
                libelle: libelle,
                pu: pu,
                qte: qte,
                montant: pu * qte
            });
        }
    }


    
    if (details.length === 0) {
        alert('Veuillez ajouter au moins un détail de devis');
        return;
    }

    const devisData = {
        demandeId: demandeRecherchee.id,
        devisTypeId: parseInt(devisTypeId),
        dateHeure: new Date().toISOString(),
        details: details
    };
    
    const xhr = new XMLHttpRequest();
    const url = '/devis/creer-devis';
    
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert('Devis créé !');
                document.getElementById('devisForm').reset();
                document.getElementById('tableBody').innerHTML = '';
                demandeRecherchee = null;
                ligneCounter = 0;
                ajouterLigne();
            } else {
                alert('Erreur lors de la création du devis');
            }
        }
    };
    
    xhr.onerror = function() {
        alert('Erreur de connexion au serveur');
    };
    
    xhr.send(JSON.stringify(devisData));
}