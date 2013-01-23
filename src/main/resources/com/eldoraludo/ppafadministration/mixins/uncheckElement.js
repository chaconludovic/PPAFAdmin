(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureUncheckElement: function(specs) {
            $('#' + specs.clientId).bind('click', function(e) {
                if($("input[name='" + specs.elementCible+"']:checked").length > 0)
                {
                    $("input[name='" + specs.elementCible+"'").click();
                    return true;
                };
                    return true;
            });
         }
    });
})(jQuery);