$(document).ready(function() {

    $(".stationSelect").autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "api/stationsTitleByPattern",
                dataType: "json",
                data: {
                    pattern: request.term
                },
                success: function( data ) {
                    response( data );
                }
            });
        },
        minLength: 3
    });

    $(".stationSwap").click(function() {
        var from = $("#from").val();
        var to = $("#to").val();

        $("#from").val(to);
        $("#to").val(from);
    });

    $.datepicker.regional['cs'] = {
        closeText: 'Zavřít',
        prevText: 'Předchozí',
        nextText: 'Další',
        currentText: 'Hoy',
        monthNames: ['Leden','Únor','Březen','Duben','Květen','Červen', 'Červenec','Srpen','Září','Říjen','Listopad','Prosinec'],
        monthNamesShort: ['Le','Ún','Bř','Du','Kv','Čn', 'Čc','Sr','Zá','Ří','Li','Pr'],
        dayNames: ['Neděle','Pondělí','Úterý','Středa','Čtvrtek','Pátek','Sobota'],
        dayNamesShort: ['Ne','Po','Út','St','Čt','Pá','So'],
        dayNamesMin: ['Ne','Po','Út','St','Čt','Pá','So'],
        weekHeader: 'Sm',
        dateFormat: 'dd.mm.yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''};

    $.datepicker.setDefaults($.datepicker.regional['cs']);
    $.datepicker.setDefaults({
        showOn: "both",
        buttonImageOnly: true,
        buttonImage: "resources/images/calendar.png",
        buttonText: "Calendar"
    });
    $("#date").datepicker();

    $('#time').timepicker({
        defaultTime: "now",
        timeFormat: "H:i",
        scrollDefault: 'now',
        step: 15
    });

    $("#noTransfer").change(function() {
        if(this.checked) {
            $("#maxTransfersDiv").hide();
        } else {
            $("#maxTransfersDiv").show();
        }
    });

});
