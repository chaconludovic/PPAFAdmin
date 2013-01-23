(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureWatermark: function(specs) {
            $('#' + specs.clientId).watermark(specs.placeholder);

         }
    });
})(jQuery);