$(document).ready(function(){
    $('#trigger').click(function() {
        $(this).next('#login').slideToggle();
    });
});