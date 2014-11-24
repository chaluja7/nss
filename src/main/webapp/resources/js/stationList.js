$(document).ready(function() {

    // Setup - add a text input to each footer cell
    $('#dataTableList tfoot th').each( function () {
        var title = $('#dataTableList thead th').eq( $(this).index() ).text();
        $(this).html( '<input type="text" placeholder="' + title + '..." />' );
    } );

    var table = $("#dataTableList").DataTable( {
        "bProcessing": false,
        "bServerSide": false,
        "sort": "title",
        "sAjaxSource": "../api/stationsDataTable",
        "aaSorting": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": "title" },
            { "mData": "name" }
        ],
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

    // Apply the search
    table.columns().eq( 0 ).each( function ( colIdx ) {
        $( 'input', table.column( colIdx ).footer() ).on( 'keyup change', function () {
            table
                .column( colIdx )
                .search( this.value )
                .draw();
        } );
    } );

} );