$(document).ready(function() {

    var table = $("#dataTableList").DataTable( {
        "sAjaxSource": "../api/stationsDataTable",
        "aoColumns": [
            {"mData": "title"},
            {"mData": "name"},
            {"mData": "region"}
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