(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureHideElement: function(specs) {
            $('#' + specs.clientId).bind('change', function(e) {
                var elementsCible = specs.elementCible.split(", "),
                    i, l
                ;
                for (i = 0, l = elementsCible.length; i < l; i ++) {
                    if ($("#" + elementsCible[i]).is(":visible")) {
                        $("#" + elementsCible[i]).hide();
                    };
                }
            });

         }
    });
})(jQuery);