/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
 $(document).ready(function () {
     $("#drop").click(function () {
         $("#login").slideToggle("slow");
     });
 });

