let ligneCounter = 0;

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('ajouterLigne').addEventListener('click', ajouterLigne);
    document.getElementById('detailsForm').addEventListener('submit', envoyerDetails);
    
    ajouterLigne();
});

function ajouterLigne() {
    const tbody = document.getElementById('tableBody');
    const id = ligneCounter++;

    const tr = document.createElement('tr');

    tr.innerHTML = `
        <td><input type="text" class="libelle" required></td>
        <td><input type="number" class="pu" value="0"></td>
        <td><input type="number" class="qte" value="0"></td>
        <td><span class="montant">0</span></td>
        <td><button type="button" onclick="this.parentNode.parentNode.remove()">X</button></td>
    `;

    tbody.appendChild(tr);

    const pu = tr.querySelector('.pu');
    const qte = tr.querySelector('.qte');
    const montant = tr.querySelector('.montant');

    function calcul() {
        montant.textContent = (pu.value * qte.value).toFixed(2);
    }

    pu.addEventListener('input', calcul);
    qte.addEventListener('input', calcul);
}

function envoyerDetails(e) {
    e.preventDefault();

    const devisId = document.getElementById('devisId').value;
    const lignes = document.querySelectorAll('#tableBody tr');

    let details = [];

    lignes.forEach(ligne => {
        const libelle = ligne.querySelector('.libelle').value;
        const pu = parseFloat(ligne.querySelector('.pu').value);
        const qte = parseFloat(ligne.querySelector('.qte').value);

        if (libelle && pu > 0 && qte > 0) {
            details.push({ libelle, pu, qte });
        }
    });

    fetch(`/devis/${devisId}/ajouter-details`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(details)
    })
    .then(res => {
        if (res.ok) {
            alert("Détails ajoutés !");
            location.reload();
        } else {
            alert("Erreur");
        }
    });
}