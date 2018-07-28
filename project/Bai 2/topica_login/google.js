
/* Login with Google*/

function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	$(".g-signin2").css("display","none");
	$(".data").css("display","block");
	$("#name").text('Hello ' + profile.getName())
	$("#pic").attr('src',profile.getImageUrl());
	$("#email").text('Email: ' + profile.getEmail());
	$("#id").text('ID: ' + profile.getId());
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
    alert("You have been successfully signed out");
    console.log("signed out");
    $(".g-signin2").css("display","block");
    $(".data").css("display","none");
    });
}