$.extend(true, $.fn.dataTable.defaults, {
    "bProcessing": true,
    "aaSorting": [[0, "asc"]],
    "oLanguage": {
        "sLengthMenu": "Zobrazit _MENU_ záznamů",
        "sZeroRecords": "Žádné záznamy nenalezeny",
        "sInfo": "Zobrazeny záznamy _START_ až _END_ z _TOTAL_ záznamů",
        "sInfoEmpty": "Zobrazeno 0 - 0 z 0 záznamů",
        "sInfoFiltered": "(vyfiltrováno z _MAX_ celkových záznamů)",
        "sSearch": "Hledat:",
        "oPaginate": {
            "sPrevious": "Předchozí",
            "sNext": "Další",
            "sFirst": "První",
            "sLast": "Poslední"
        }
    }
});

function setUpDataTablesInlineFinding(tableVar) {
    // Apply the search
    tableVar.columns().eq(0).each(function(colIdx) {
        $('input', tableVar.column(colIdx).footer()).on('keyup change', function() {
            tableVar.column(colIdx).search(this.value).draw();
        });
    });
}

$(document).ready(function() {
    // Setup - add a text input to each footer cell
    $('#dataTableList tfoot th').each( function () {
        var title = $('#dataTableList thead th').eq( $(this).index() ).text();
        $(this).html( '<input type="text" placeholder="' + title + '..." />' );
    } );
});
