(function ($) {

    $.extend(Tapestry.Initializer, {
        setupSmartDatepicker:function (specs) {
            $.datepicker._defaults.constrainInput = false;
            $.datepicker._defaults.dateFormat = specs.dateFormat;
            $.datepicker._defaults.changeMonth = true;
            $.datepicker._defaults.changeYear = true;
            $.datepicker._defaults.yearRange= "-5:+25";
            $.datepicker._defaults.showOtherMonths = true;
            $.datepicker._defaults.buttonImageOnly = true;
            $.datepicker._defaults.selectOtherMonths = true;
            $.datepicker._defaults.showOn = 'button';
            $.datepicker._defaults.buttonText = ' ';
            $.datepicker._defaults.buttonImage = '/images/cautions/ico_calendar.png';
            $("#" + specs.elementId).datepicker().bind('blur', function(e) {
                var champ = $(e.target);
                var valeur = champ.val();
                var dateFormat = champ.datepicker( "option", "dateFormat" );
                var regexpSansSlash, regexpSansSlashAnneeADeuxChiffres;

                // Gestion format date sans slash
                if (dateFormat=="yy/mm/dd") {
                    regexpSansSlash = /^(\d{4})(\d{2})(\d{2})$/;
                    regexpSansSlashAnneeADeuxChiffres = /^(\d{2})(\d{2})(\d{2})$/;
                } else {
                    regexpSansSlash = /^(\d{2})(\d{2})(\d{4})$/;
                    regexpSansSlashAnneeADeuxChiffres = /^(\d{2})(\d{2})(\d{2})$/;
                }

                var valeurSansSlash = regexpSansSlash.exec(valeur);
                if (valeurSansSlash != null) {
                    var valeurAvecSlash = valeurSansSlash[1] + "/" + valeurSansSlash[2] + "/" + valeurSansSlash[3];
                    champ.val(valeurAvecSlash);
                    validateDatePickerContent();
                    return;
                }
                var valeurSansSlash2 = regexpSansSlashAnneeADeuxChiffres.exec(valeur);
                if (valeurSansSlash2 != null) {
                    var valeurAvecSlash;
                    if (dateFormat=="yy/mm/dd") {
                        valeurAvecSlash = "20" + valeurSansSlash2[1] + "/" + valeurSansSlash2[2] + "/" + valeurSansSlash2[3];
                    } else {
                        valeurAvecSlash = valeurSansSlash2[1] + "/" + valeurSansSlash2[2] + "/" + "20" + valeurSansSlash2[3];
                    }
                    champ.val(valeurAvecSlash);
                    validateDatePickerContent();
                    return;
                }

                // Regexp +/-
                var regexpAnnees = /([+-]\d{1,2})[YyAa]/;
                var regexpMois = /([+-]\d{1,3})[Mm]/;
                var regexpJours = /([+-]\d{1,5})[DdJj]/;

                // Récupère la quantité à additionner (quantité signée)
                function quantite(parsedString) {
                    return parsedString ? Number( parsedString[1] ) : 0;
                }

                function validateDatePickerContent() {
                    try {
                        $.datepicker.parseDate(dateFormat, champ.val());
                    } catch (err) {
                        champ.val('');
                    }
                }

                // exécute les regexp
                var quantiteAnnees = quantite(regexpAnnees.exec(valeur));
                var quantiteMois = quantite(regexpMois.exec(valeur));
                var quantiteJours = quantite(regexpJours.exec(valeur));

                if (quantiteMois != 0 || quantiteJours != 0 || quantiteAnnees != 0) {
                    // si on a réussi à parser l'entrée on met à jour le champ avec la valeur calculée
                    var date = new Date();
                    date.setTime(specs.dateAnalyseTime);
                    date.setFullYear(date.getFullYear() + quantiteAnnees, date.getMonth() + quantiteMois, date.getDate() + quantiteJours);
                    champ.val($.datepicker.formatDate(dateFormat, date));
                } else {
                    // sinon regarde si l'entrée est absurde, auquel cas on vide le champ
                    // (il ne faut pas supprimer la valeur dans le cas où l'utilisateur a utilisé le datepicker
                    // ou entré la date à la main...)
                    validateDatePickerContent();
                }
            });
        }


    });





})(jQuery);