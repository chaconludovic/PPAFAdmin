(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureSubmitOnEnter: function(specs) {
            $('#' + specs.clientId).bind('keypress', function(e) {
                    if(e.keyCode==13){
                        var el =        $('#' + specs.zoneCible);

                        el.tapestryZone("update", {url : specs.url, params:{valeur:$('#' + specs.clientId).val()}});
                        //$('#' + specs.clientId).focus();
                        e.preventDefault();
                    }
            });

         }
    });
})(jQuery);