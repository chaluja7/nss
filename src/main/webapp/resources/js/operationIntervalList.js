$(document).ready(function() {

    var table = $("#dataTableList").DataTable( {
        "sAjaxSource": "../api/operationIntervalsDataTable",
        "aoColumns": [
            {"mData": "id"},
            {"mData": "startDate"},
            {"mData": "endDate"},
            {"mData": "monday"},
            {"mData": "tuesday"},
            {"mData": "wednesday"},
            {"mData": "thursday"},
            {"mData": "friday"},
            {"mData": "saturday"},
            {"mData": "sunday"}
        ],
        "fnDrawCallback": function() {
            $('#dataTableList tbody tr').click(function () {
                var recordId = table.row(this).data().id;
                document.location.href = "operationinterval.xhtml?id=" + recordId;
            });
        }

    });

    setUpDataTablesInlineFinding(table);

} );