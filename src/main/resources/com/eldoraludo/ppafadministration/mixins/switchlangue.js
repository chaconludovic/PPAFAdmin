(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        switchLangue: function(specs) {
            $('#' + specs.clientId + ' .pays-links').click(function() {
                $('#' + specs.clientId + ' .pays-links ul').show();
                $('#' + specs.clientId + ' .pays-links div').addClass("on");
            }).mouseleave(function() {
                $('#' + specs.clientId + ' .pays-links ul').hide();
                $('#' + specs.clientId + ' .pays-links div').removeClass("on");
            });
         }
    });
})(jQuery);