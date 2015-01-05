$(document).ready(function() {

    var table = $("#dataTableList").DataTable( {
        "sAjaxSource": "../api/routesDataTable",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "name"},
            {"mData": "stationFrom"},
            {"mData": "stationTo"}
        ],
        "fnDrawCallback": function() {
            $('#dataTableList tbody tr').click(function () {
                var recordId = table.row(this).data().id;
                document.location.href = "route.xhtml?id=" + recordId;
            });
        }
    });

    setUpDataTablesInlineFinding(table);

} );