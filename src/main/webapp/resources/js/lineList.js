$(document).ready(function() {

    var table = $("#dataTableList").DataTable( {
        "sAjaxSource": "../api/linesDataTable",
        "aoColumns": [
            {"mData": "name"},
            {"mData": "route"},
            {"mData": "type"}
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