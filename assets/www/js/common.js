
function showAndroidToast(toast) {
    Android.showToast(toast);
}
function addAccount(name, pass) {
    Android.addAccount(name, pass);
}

function displayAccounts(){
	var accountsJson = jQuery.parseJSON(Android.getAllAccounts());
	var display = "<ul>";
	$.each(accountsJson, function(){
		display += "<li>"+this['name']+"</li>";
	});
	display += "</ul>";
	$("#msg").html(display);
}

//document is ready
$(function() {
	$("#submit").click(function(){
		var name = $("#name").val();
		var pass = $("#pass").val();
		addAccount(name, pass);
		displayAccounts();
	});
	displayAccounts();
});