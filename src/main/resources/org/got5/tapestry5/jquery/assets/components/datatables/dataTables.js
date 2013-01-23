(function ($) {
    T5.extendInitializers(function () {

        function init(specs) {
            jQuery.extend(specs.params, {"oLanguage":{"sUrl":"/js/datatables/datatables_" + specs.params.lang + ".txt"}});

            jQuery.extend(specs.params, {"fnInitComplete":function (oSettings, json) {
                jQuery('#' + specs.params.filterId + ' > label > input').watermark(oSettings.oLanguage.sSearch);
            }});

            jQuery.extend(specs.params, {"fnServerData": function( sUrl, aoData, fnCallback, oSettings ) {
                oSettings.jqXHR = $.ajax( {
                        "url": sUrl,
                        "data": aoData,
                        "success": fnCallback,
                        "dataType": "jsonp",
                        "cache": false
                    } );
            }});

            var oTable = $("#" + specs.id).dataTable(specs.params);
            var oSettings = oTable.fnSettings();

            /*oTable.$('.toggleactif').click( function () {
                var cell = $(this).parent("td");
                var sData = oTable.fnGetData(cell);
                alert( 'The cell clicked on had the value of '+sData );
              } );
            */
            /* Show an example parameter from the settings */
            //alert( oSettings._iDisplayStart );
            //alert( oSettings.aoColumns.length );
            //alert( oSettings.aoColumns );

            //$('#datatable_filter > input').watermark(specs.placeholder);
        }

        return {
            dataTable:init
        }
    });

})(jQuery);

