(function($) {
    /** Mixin simple permettant d'enregistrer une action actuate sur le click d'un composant */
    $.extend(Tapestry.Initializer, {
        updateZoneOnClientEvent: function (specs) {
            var element = $("#" + specs.element);
            for (var i in specs.events) {

                $(document).on(specs.events[i].event,
                    function(e) {
                        $("#"+specs.element).tapestryZone("update", specs.events[i]);
                    }
                );
            }
        }
    });
})(jQuery);