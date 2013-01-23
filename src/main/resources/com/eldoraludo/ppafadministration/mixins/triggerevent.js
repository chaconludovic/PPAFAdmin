(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureTriggerEvent: function(specs) {
            $('#' + specs.clientId).bind('keypress', function(e) {
                    if(e.keyCode==13){

                            $('#' + specs.clientId).change();
                            $('#' + specs.clientId).trigger('change');
                            $('#' + specs.clientId).trigger('blur');
                            $('#' + specs.elementCible).trigger(specs.actionCible);
                            $('#' + specs.clientId).focus();
                    }
            });

         }
    });
})(jQuery);