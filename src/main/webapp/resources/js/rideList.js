$(document).ready(function() {

    var mySelect = $('#tableData').prev().find('select');
    var selectedLineId = mySelect.val();
    var table = $("#dataTableList").DataTable( {
        "sAjaxSource": "../api/ridesDataTable/" + selectedLineId,
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

    mySelect.change(function() {
        selectedLineId = mySelect.val();
        table.ajax.url("../api/ridesDataTable/" + selectedLineId).load();
    });

} );