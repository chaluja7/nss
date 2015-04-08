$(document).ready(function() {

    var table = $("#dataTableList").DataTable( {
        "serverSide": true,
        "processing": true,
        "ajax": {
            "url": "../api/linesDataTable",
            "type": "POST",
            "dataType": "json",
            "contentType": "application/json",
            "data": function(d) {
                return JSON.stringify(d);
            }
        },
        "columns": [
            {"data": "name"},
            {"data": "route"},
            {"data": "type"}
        ],
        "fnDrawCallback": function() {
            $('#dataTableList tbody tr').click(function () {
                var recordId = table.row(this).data().id;
                document.location.href = "line.xhtml?id=" + recordId;
            });
        }

    });

    setUpDataTablesInlineFinding(table);

} );