(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureFocusOnZone: function(specs) {
            $('#' + specs.zoneId).bind(Tapestry.ZONE_UPDATED_EVENT, function(e) {
                            $('input[name^=' + specs.elementCible + ']').focus();
            });

         }
    });
})(jQuery);