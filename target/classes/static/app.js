// API Base URL
const API_URL = 'http://localhost:8080/api';

// Éléments DOM
const clientsTable = document.getElementById('clientsTable');
const demandesTable = document.getElementById('demandesTable');
const clientForm = document.getElementById('clientForm');
const demandeForm = document.getElementById('demandeForm');
const demandeClient = document.getElementById('demandeClient');

// Statistiques
const clientCount = document.getElementById('clientCount');
const demandeCount = document.getElementById('demandeCount');
const totalDevis = document.getElementById('totalDevis');

// Charger les données au démarrage
document.addEventListener('DOMContentLoaded', function() {
    loadClients();
    loadDemandes();
    updateStatistics();
    
    // Mettre la date du jour par défaut
    document.getElementById('demandeDate').valueAsDate = new Date();
});

// Gestion du formulaire Client
clientForm.addEventListener('submit', function(e) {
    e.preventDefault();
    
    const client = {
        nom: document.getElementById('clientNom').value,
        contact: document.getElementById('clientContact').value,
        devis: parseFloat(document.getElementById('clientDevis').value)
    };
    
    fetch(`${API_URL}/clients`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(client)
    })
    .then(response => response.json())
    .then(data => {
        clientForm.reset();
        loadClients();
        updateStatistics();
        showNotification('Client ajouté avec succès!', 'success');
    })
    .catch(error => {
        console.error('Erreur:', error);
        showNotification('Erreur lors de l\'ajout du client', 'error');
    });
});

// Gestion du formulaire Demande
demandeForm.addEventListener('submit', function(e) {
    e.preventDefault();
    
    const demande = {
        date: document.getElementById('demandeDate').value,
        district: document.getElementById('demandeDistrict').value,
        devis: parseFloat(document.getElementById('demandeDevis').value),
        client: {
            id: parseInt(document.getElementById('demandeClient').value)
        }
    };
    
    fetch(`${API_URL}/demandes`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(demande)
    })
    .then(response => response.json())
    .then(data => {
        demandeForm.reset();
        document.getElementById('demandeDate').valueAsDate = new Date();
        loadDemandes();
        updateStatistics();
        showNotification('Demande ajoutée avec succès!', 'success');
    })
    .catch(error => {
        console.error('Erreur:', error);
        showNotification('Erreur lors de l\'ajout de la demande', 'error');
    });
});

// Charger tous les clients
function loadClients() {
    fetch(`${API_URL}/clients`)
        .then(response => response.json())
        .then(clients => {
            displayClients(clients);
            updateClientSelect(clients);
        })
        .catch(error => {
            console.error('Erreur:', error);
            clientsTable.innerHTML = '<tr><td colspan="4" style="text-align: center; color: red;">Erreur de chargement</td></tr>';
        });
}

// Afficher les clients dans le tableau
function displayClients(clients) {
    if (clients.length === 0) {
        clientsTable.innerHTML = '<tr><td colspan="4" style="text-align: center; color: #666;">Aucun client trouvé</td></tr>';
        return;
    }
    
    clientsTable.innerHTML = clients.map(client => `
        <tr>
            <td>${client.id}</td>
            <td><strong>${client.nom}</strong></td>
            <td>${client.contact}</td>
            <td>${client.devis.toFixed(2)} €</td>
            <td>
                <button onclick="deleteClient(${client.id})" class="btn-danger">Supprimer</button>
            </td>
        </tr>
    `).join('');
}

// Mettre à jour le select des clients pour les demandes
function updateClientSelect(clients) {
    demandeClient.innerHTML = '<option value="">Choisir un client...</option>' +
        clients.map(client => `<option value="${client.id}">${client.nom}</option>`).join('');
}

// Charger toutes les demandes
function loadDemandes() {
    fetch(`${API_URL}/demandes`)
        .then(response => response.json())
        .then(demandes => {
            displayDemandes(demandes);
        })
        .catch(error => {
            console.error('Erreur:', error);
            demandesTable.innerHTML = '<tr><td colspan="6" style="text-align: center; color: red;">Erreur de chargement</td></tr>';
        });
}

// Afficher les demandes dans le tableau
function displayDemandes(demandes) {
    if (demandes.length === 0) {
        demandesTable.innerHTML = '<tr><td colspan="6" style="text-align: center; color: #666;">Aucune demande trouvée</td></tr>';
        return;
    }
    
    demandesTable.innerHTML = demandes.map(demande => `
        <tr>
            <td>${demande.id}</td>
            <td>${formatDate(demande.date)}</td>
            <td>${demande.district}</td>
            <td>${demande.devis.toFixed(2)} €</td>
            <td><strong>${demande.client ? demande.client.nom : 'N/A'}</strong></td>
            <td>
                <button onclick="deleteDemande(${demande.id})" class="btn-danger">Supprimer</button>
            </td>
        </tr>
    `).join('');
}

// Supprimer un client
function deleteClient(id) {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce client ?')) {
        fetch(`${API_URL}/clients/${id}`, {
            method: 'DELETE'
        })
        .then(() => {
            loadClients();
            loadDemandes();
            updateStatistics();
            showNotification('Client supprimé avec succès!', 'warning');
        })
        .catch(error => {
            console.error('Erreur:', error);
            showNotification('Erreur lors de la suppression', 'error');
        });
    }
}

// Supprimer une demande
function deleteDemande(id) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette demande ?')) {
        fetch(`${API_URL}/demandes/${id}`, {
            method: 'DELETE'
        })
        .then(() => {
            loadDemandes();
            updateStatistics();
            showNotification('Demande supprimée avec succès!', 'warning');
        })
        .catch(error => {
            console.error('Erreur:', error);
            showNotification('Erreur lors de la suppression', 'error');
        });
    }
}

// Mettre à jour les statistiques
function updateStatistics() {
    Promise.all([
        fetch(`${API_URL}/clients`),
        fetch(`${API_URL}/demandes`)
    ])
    .then(([clientsResponse, demandesResponse]) => 
        Promise.all([clientsResponse.json(), demandesResponse.json()])
    )
    .then(([clients, demandes]) => {
        const nbClients = clients.length;
        const nbDemandes = demandes.length;
        const totalDevisValue = clients.reduce((sum, client) => sum + (client.devis || 0), 0);
        
        clientCount.textContent = nbClients;
        demandeCount.textContent = nbDemandes;
        totalDevis.textContent = totalDevisValue.toFixed(2);
    })
    .catch(error => {
        console.error('Erreur statistiques:', error);
    });
}

// Formater la date
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR');
}

// Afficher les notifications
function showNotification(message, type) {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.remove();
    }, 3000);
}
