/*
* A Javascript script that will populate an accounts table without adding any extra selection items.
*
*/

let selectAccount;

window.onload = function(){
	//console.log("Inside onload function!");
	grabUserAccounts();
	document.getElementById("selectedAccount").addEventListener('keyup', setSelectAccount);
	document.getElementById("displayAccounts").addEventListener('click', grabAUsersAccounts);
}

function grabUserAccounts(){
	let xhr = new XMLHttpRequest();
	const url = "allaccounts";
	
	xhr.onreadystatechange = function(){
		//console.log("Grabbing accounts now!");
		switch(xhr.readyState){
			case 0:
				//console.log("nothing, not initalized yet!");
				break;
			case 1: 
				//console.log("connection established");
				break;
			case 2:
				//console.log("request sent");
				break;
			case 3:
				//console.log("awaiting request");
				break;
			case 4: 
				//console.log(xhr.status);
				
				if(xhr.status == 200){
					//console.log(xhr.responseText);
					let userList = JSON.parse(xhr.responseText);
					//console.log(accountList);
					
					userList.forEach(
						element => {
							addUserRow(element);
						}
					)
				}
		}//end switch	
	}
	xhr.open("GET",url);
		xhr.send();
}


function addUserRow(user){
	//Get the table
	let table = document.getElementById("user-table")
	
	//Create the row and elements
	let tableRow = document.createElement("tr");
	//let selectedCol = document.createElement("td");
	//let selection = document.createElement("input");
	let iDCol = document.createElement("td");
	let firstNameCol = document.createElement("td");
	let lastNameCol = document.createElement("td");
	let usernameCol = document.createElement("td");
	
	//Create separate input for the selection input
	//selection.classList.add("form-check-input");
	//selection.setAttribute("type", "radio");
	//selection.setAttribute("name", "account");
	//selection.setAttribute("value", account.acc_id);
	//selection.setAttribute("required", "");
	//console.log(selection);
	
	//Add selection input to selection column
	//selectedCol.appendChild(selection);
	
	//Add data items to table row
	//tableRow.appendChild(selectedCol);
	tableRow.appendChild(iDCol);
	tableRow.appendChild(firstNameCol);
	tableRow.appendChild(lastNameCol);
	tableRow.appendChild(usernameCol);
	
	//Add row to table
	table.appendChild(tableRow);
	
	//Insert data into other data items
	iDCol.innerHTML = user.user_id;
	firstNameCol.innerHTML = user.first_name;
	lastNameCol.innerHTML = user.last_name;
	usernameCol.innerHTML = user.user_name;
};

function setSelectAccount() {
	selectAccount = document.getElementById("selectedAccount").value;
};

function grabAUsersAccounts(){
	let xhr = new XMLHttpRequest();
	const url = "useraccounts";
	var formData = new FormData();
	//console.log(selectAccount);
	//console.log(document.getElementById("selectedAccount"));
	
	formData.append("userID", document.getElementById("selectedAccount").value);
	
	xhr.onreadystatechange = function() {
		//console.log("Grabbing accounts now!");
		switch(xhr.readyState){
			case 0:
				//console.log("nothing, not initalized yet!");
				break;
			case 1: 
				//console.log("connection established");
				break;
			case 2:
				//console.log("request sent");
				break;
			case 3:
				//console.log("awaiting request");
				break;
			case 4: 
				//console.log(xhr.status);
				
				if(xhr.status == 200){
					//console.log(xhr.responseText);
					let accountList = JSON.parse(xhr.responseText);
					//console.log(accountList);
					
					accountList.forEach(
						element => {
							addRow(element);
						}
					)
				}
		}//end switch	
	}
	xhr.open("POST",url);
		xhr.send(formData);
}


function addRow(account) {
	//Get the table
	let table = document.getElementById("account-table")
	
	//Create the row and elements
	let tableRow = document.createElement("tr");
	//let selectedCol = document.createElement("td");
	//let selection = document.createElement("input");
	let iDCol = document.createElement("td");
	let descriptionCol = document.createElement("td");
	let amountCol = document.createElement("td");
	
	//Create separate input for the selection input
	//selection.classList.add("form-check-input");
	//selection.setAttribute("type", "radio");
	//selection.setAttribute("name", "account");
	//selection.setAttribute("value", account.acc_id);
	//selection.setAttribute("required", "");
	//console.log(selection);
	
	//Add selection input to selection column
	//selectedCol.appendChild(selection);
	
	//Add data items to table row
	//tableRow.appendChild(selectedCol);
	tableRow.appendChild(iDCol);
	tableRow.appendChild(descriptionCol);
	tableRow.appendChild(amountCol);
	
	//Add row to table
	table.appendChild(tableRow);
	
	//Insert data into other data items
	iDCol.innerHTML = account.acc_id;
	descriptionCol.innerHTML = account.acc_type;
	amountCol.innerHTML = `$ ` + parseFloat(account.acc_amount).toFixed(2);
};


