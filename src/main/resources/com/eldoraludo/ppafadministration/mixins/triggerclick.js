(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureTriggerClick: function(specs) {
            $('#' + specs.clientId).bind('click', function(e) {
                $('#' + specs.elementCible).trigger(specs.actionCible);
                $('#' + specs.elementCible).click();
            });

         }
    });
})(jQuery);