function onCheck()
{
    var form = document.getElementById("registerform");
    var user_val = form.elements["user_id"].value;
    getData_sync("/kaambanega/register/checkAvailability", "availableDiv", "user_id=" + user_val, false);
}

function validateForm(){
	var form = document.getElementById("registerform");
	var username = form.elements["user_id"].value;
	var password = form.elements["password"].value;
	var rpassword = form.elements["rpassword"].value;
	var expr = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	var email = form.elements["email_id"].value;
	
	var error = false;
	var msg = '';
	if(username == ''){
		msg+='Username should not be empty\n';
		error=true;
	}
	if(password.length<8 || password.length>32){
		msg+='Password must be between 8 to 32 characters long\n'; 
		error=true;
	}
	if(rpassword!=password){
		msg += 'Both entered passwords should match\n';
		error=true;
	}
	if(email == ''){
		msg += 'Email should not be empty\n';
		error=true;
	}
	if(!expr.test(email)){
		msg+= 'The email address you have entered is not valid.\n';
		error=true;
	}
	if(msg!=''){
		alert(msg);
	}
//	return !error;
	if (!error){
		getData_sync("/kaambanega/register/newUser", "messageDiv", getalldata(form), false);
	}
}