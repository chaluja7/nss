$(document).ready(function() {

    var table = $("#dataTableList").DataTable( {
        "sAjaxSource": "../api/ridesDataTable",
        "aoColumns": [
            {"mData": "line"},
            {"mData": "departure"},
            {"mData": "arrival"},
            {"mData": "duration"},
            {"mData": "stationFrom"},
            {"mData": "stationTo"}
        ],
        "fnDrawCallback": function() {
            $('#dataTableList tbody tr').click(function () {
                var recordId = table.row(this).data().id;
                document.location.href = "ride.xhtml?id=" + recordId;
            });
        }

    });

    setUpDataTablesInlineFinding(table);

} );