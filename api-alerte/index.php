<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sivi d'Alertes - Forage</title>
    <link rel="stylesheet" href="./bootstrap.min.css">
    <style>
        body { background-color: #f8f9fa; padding-top: 2rem; }
        .card { border-radius: 15px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
        .table thead { background-color: #e9ecef; }
        .badge-rouge { background-color: #dc3545; }
        .badge-orange { background-color: #fd7e14; }
        .badge-jaune { background-color: #ffc107; color: #212529; }
        .badge-info { background-color: #0dcaf0; }
        .alert-item { display: inline-block; margin: 2px; padding: 5px 10px; border-radius: 20px; font-size: 0.8rem; font-weight: 500; }
    </style>
</head>
<body>
    <div class="container">
        <div class="row mb-4">
            <div class="col-12">
                <div class="card p-4">
                    <h2 class="mb-4 text-center">Tableau de Bord des Alertes</h2>
                    <div class="row g-3 align-items-end">
                        <div class="col-md-5">
                            <label for="refDemande" class="form-label fw-bold">Filtrer par Demande spécifique</label>
                            <select id="refDemande" class="form-select">
                                <option value="">Toutes les demandes avec alertes</option>
                            </select>
                        </div>
                        <div class="col-md-5">
                            <label for="filterText" class="form-label fw-bold">Recherche rapide (Référence, Client, Commune)</label>
                            <input type="text" id="filterText" class="form-control" placeholder="Entrez un mot-clé...">
                        </div>
                        <div class="col-md-2">
                            <button id="btnRefresh" class="btn btn-primary w-100">Actualiser</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-12">
                <div class="card p-0 overflow-hidden">
                    <div class="table-responsive">
                        <table class="table table-hover mb-0" id="alertsTable">
                            <thead>
                                <tr>
                                    <th>Référence</th>
                                    <th>Client</th>
                                    <th>Commune</th>
                                    <th>Date Demande</th>
                                    <th>Alertes Actives</th>
                                </tr>
                            </thead>
                            <tbody id="alertsBody">
                                <tr>
                                    <td colspan="5" class="text-center py-5">
                                        <div class="spinner-border text-primary" role="status">
                                            <span class="visually-hidden">Chargement...</span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="./index.js" defer></script>
</body>
</html>