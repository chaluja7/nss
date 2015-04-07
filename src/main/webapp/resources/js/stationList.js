$(document).ready(function() {

    var table = $("#dataTableList").DataTable( {
        "serverSide": true,
        "processing": true,
        "ajax": {
            "url": "../api/stationsDataTable",
            "type": "POST",
            "dataType": "json",
            "contentType": "application/json",
            "data": function(d) {
                return JSON.stringify(d);
            }
        },
        "columns": [
            {"data": "title"},
            {"data": "name"},
            {"data": "region"}
        ],
        "fnDrawCallback": function() {
            $('#dataTableList tbody tr').click(function () {
                var recordId = table.row(this).data().id;
                document.location.href = "station.xhtml?id=" + recordId;
            });
        }
    });

    setUpDataTablesInlineFinding(table);

} );