(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {
        /**
         * Links an element to a zone. The zone is updated on the given client event.
         * @param specs
         */
        linkElementToZone: function(specs) {
            var el = $("#" + specs.elementId);

            var zoneId = specs.zoneId;
            var zoneElement = zoneId === '^' ? $(el).closest('.t-zone') : $("#" + zoneId);

            var options = {
                url: specs.url,
                params: {}
            }

            //Ne marche pas sur le 'change' des texfields : à déboguer
            //En attendant, on convertit en 'blur'
            /*
            if (el && el.is("input:text") && specs.clientEvent == "change") {
                specs.clientEvent = "blur";
            }
            */

            el.bind(specs.clientEvent, function() {
                clearTimeout(this.searching);
                this.searching = setTimeout(function() {
                    // Récuperer la valeur de l'élement sur lequel l'evenement a été bindé
                    if ($(el).attr("type") === "checkbox") {
                        options.params[specs.paramName] = el.is(":checked");
                    } else {
                        options.params[specs.paramName] = el.val();
                    }
                    options.params = $.extend(options.params, zoneElement.tapestryZone("option", "parameters"));
                    zoneElement.tapestryZone("update", options);
                }, specs.delay);
            })
        }
    });
})(jQuery);
