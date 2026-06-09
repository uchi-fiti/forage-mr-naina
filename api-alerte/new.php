<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Api Alerte</title>
    <link rel="stylesheet" href="./bootstrap.min.css">
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <form id="form-api">
        <div class="row">
            <div class="form-container col-5">
                <label for="refDemande" class="form-label">Sélectionnez la référence de la demande</label>
                <select name="refDemande" id="refDemande" class="form-select mb-3">
                    <option value="">Chargement des demandes...</option>
                </select>
                <button type="submit" class="btn btn-primary">Valider</button>
            </div>
            <div class="col-7">
                <div id="results"></div>
            </div>
        </div>
    </form>
    <script src="./index.js" defer></script>
</body>
</html>