
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
	$("#submit").click(function(evt){
		evt.preventDefault();
		var name = $.trim($("#name").val());
		var pass = $.trim($("#pass").val());
		if (name != "" && pass != ""){
		 addAccount(name, pass);
		 displayAccounts();
		 $("#name").val("");
		 $("#pass").val("");
		} else {
		  showAndroidToast("Name and Password cannot be blank");
		}
		
	});
	displayAccounts();
});