// Get login and register
var login = $(".login1");
var register = $(".register");

// Get the modal
var modal = document.getElementById('id01');

// Get login and sign-up section of the modal
var cmLogin = $(".containermodalLogin");
var cmSignUp = $(".containermodalSignUp");

// When the user clicks anywhere outside of the modal, close it
$(window).on("click", function(e) {
    if (e.target===modal) {
        hideModal();
    }
});

var hideModal = function(){
    $("#id01").hide();
};

var showLoginModal = function() {
    if (!(login.hasClass("active"))){
        login.addClass("active");
        if (register.hasClass("active")) {
            register.removeClass("active");
        }
    }
    $("#id01").show();
    cmSignUp.hide();
    cmLogin.show();

};

var showSignupModal = function() {
    if (!(register.hasClass("active"))){
        register.addClass("active");
        if (login.hasClass("active")) {
            login.removeClass("active");
        }
    }
    $("#id01").show();
    cmLogin.hide();
    cmSignUp.show();
};


function ajaxTest(){
    event.preventDefault();
    var userJson = $("#userform").serializeJSON();
    $.ajax({
        method: 'POST',
        url : "/MyHours/Test",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Eyy det virker jo")
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error + "fdsfdf");
            console.log("not gooooooooood")
        }
    });
    console.log(userJson);
}



/*
function ajaxTest(){
    var userJson = $("#userform").serializeJSON();
    $.ajax({
        method: 'GET',
        url : "/MyHours/Test",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Eyy det virker jo")
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error + "hey");
            console.log("not gooooooooood")
        }
    });
console.log(userJson)}
*/