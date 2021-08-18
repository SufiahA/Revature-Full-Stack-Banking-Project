/*
* A Javascript script that will populate an accounts table while adding a 
* selection radio button to it.
*
*/

window.onload = function(){
	//console.log("Inside onload function!");
	grabAccounts();
}

function grabAccounts(){
	let xhr = new XMLHttpRequest();
	const url = "accounts";
	
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
					let accountList = JSON.parse(xhr.responseText);
					//console.log(accountList);
					
					//Add to the first table
					accountList.forEach(
						element => {
							addRow(element, "account-table-one", "accountFrom");
						}
					)
					
					//Add to the second table
					accountList.forEach(
						element => {
							addRow(element, "account-table-two", "accountTo");
						}
					)
				}
		}//end switch	
	}
	xhr.open("GET",url);
		xhr.send();
}


function addRow(account, tableID, selectionName){
	//Get the table
	let table = document.getElementById(tableID);
	
	//Create the row and elements
	let tableRow = document.createElement("tr");
	let selectedCol = document.createElement("td");
	let selection = document.createElement("input");
	let iDCol = document.createElement("td");
	let descriptionCol = document.createElement("td");
	let amountCol = document.createElement("td");
	
	//Create separate input for the selection input
	selection.classList.add("form-check-input");
	selection.setAttribute("type", "radio");
	selection.setAttribute("name", selectionName);
	selection.setAttribute("value", account.acc_id);
	selection.setAttribute("required", "");
	//console.log(selection);
	
	//Add selection input to selection column
	selectedCol.appendChild(selection);
	
	//Add data items to table row
	tableRow.appendChild(selectedCol);
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