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


function Login(){
    event.preventDefault();
    var userJson = $("#LoginInfo input").serializeJSON();
    $.ajax({
        method: 'POST',
        url : "MyHours/ArrayDBController/loginCheck",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            if (data.toString() === "true"){
                window.location='PersonalSite.html';
            }
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
        }
    });
}

function Signup() {
    event.preventDefault();
    var userJson = $("#usersign input").serializeJSON();
    $.ajax({
        method: 'POST',
        url: "MyHours/ArrayDBController/createWorker",
        data: userJson,
        contentType: "application/json",
        success : function () {
            alert("User created!");

        },
        error: function (jqXHR, text, error) {
            alert(jqXHR.status + text + error);
        }
    });
}
