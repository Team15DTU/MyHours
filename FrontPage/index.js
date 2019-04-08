/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
/*
FIXME: Hvis man klikker to gange hurtigt efter hinanden så vil begge poppe op
 */

 $(document).ready(function () {
     $("#drop").click(function () {
         $("#signup").slideUp("slow");
         $.when( $("#signup").slideUp("slow")).done(function () {
             $("#login").slideToggle("slow");
         });
     });
 });
$(document).ready(function () {
    $("#dropSign").click(function () {
        $("#login").slideUp("slow");
        $.when( $("#login").slideUp("slow")).done(function () {
            $("#signup").slideToggle("slow");
        });
    });
});


