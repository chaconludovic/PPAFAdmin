(function($) {
    /** Container of functions that may be invoked by the Tapestry.init() function. */
    $.extend(Tapestry.Initializer, {

        configureScrollableTab: function(specs) {
                $('.comptes-tabs-slider').each(function(){
                    if($('li:last',this).width()+$('li:last',this).offset().left-$('li:first',this).offset().left>$('div',this).width()){
                        // enable the buttons
                        $('button',this).css('display','inline');
                        $('button.prev',this).css('visibility','hidden');
                    }
                });


                $(".comptes-tabs-slider .next").click(function(){
                    //Remove the exist selector
                    //Set the width to the widest of either
                    var $div =$('div',this.parentNode)
                        ,maxoffset = $('li:last',$div).width()+$('li:last',$div).offset().left - $('li:first',$div).offset().left - $div.width()
                        ,offset = Math.abs(parseInt( $('ul',$div).css('marginLeft') ))
                        ,diff = $div.width();

                    if( offset >= maxoffset )
                        return;
                    else if ( offset + diff >= maxoffset ){
                        diff = maxoffset - offset + 20;
                        // Hide this
                        $(this).css('visibility','hidden');
                    }
                    // enable the other
                    $('.prev', this.parentNode).css('visibility','visible');

                    $("ul", $(this).parent() ).animate({
                        marginLeft: "-=" + diff
                    },400, 'swing');
                });

                $(".comptes-tabs-slider .prev").click(function(){

                    var offset = Math.abs(parseInt( $('ul',this.parentNode).css('marginLeft') ));
                    var diff = $('div',this.parentNode).width();
                    if( offset <= 0 )
                        return;
                    else if ( offset - diff <= 0 ){
                        $(this).css('visibility','hidden');
                        diff = offset;
                    }
                    $('.next', this.parentNode).css('visibility','visible');

                    $("ul",$(this).parent()).animate({
                        marginLeft: '+='+diff
                    },400, 'swing');
                });
         }
    });
})(jQuery);