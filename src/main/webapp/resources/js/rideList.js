$(document).ready(function() {

    var mySelect = $('#tableData').prev().find('select');

    var line = getUrlParameter('line');
    if(typeof line !== 'undefined') {
        mySelect.val(line);
    }

    var selectedLineId = mySelect.val();
    var table = $("#dataTableList").DataTable( {
        "sAjaxSource": "../api/ridesDataTable/" + selectedLineId,
        "aoColumns": [
            {"mData": "departure"},
            {"mData": "arrival"},
            {"mData": "duration"},
            {"mData": "stationFrom"},
            {"mData": "stationTo"},
            {"mData": "operationInterval"}
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

function getUrlParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++)
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam)
        {
            return sParameterName[1];
        }
    }
}