(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureToolTip: function(specs) {
            $('#' + specs.clientId).tooltip({
                    title: specs.title
                });
         }
    });
})(jQuery);

