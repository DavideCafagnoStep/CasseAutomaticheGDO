<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<title>GDO Casse Automatuche</title>
</head>
<body>
<script>
function renderStock(stock){
    res =`<div class=" mt-4 p-5 border-1 border-primary shadow">
    <div class="row p-1 mb-3">
        <div class="col text-center"><b>ID</b></div>
        <div class="col text-center"><b>PRODUCT</b></div>
        <div class="col text-center"><b>STOCK</b></div>
        <div class="col text-center"><b>DATE</b></div>
    </div>
    `;
for(s of stock){
res +=`
<div class="row p-1">
    <div class="col text-center">`+ s.id +`</div>
    <div class="col text-center">`+s.product.description+`</div>
    <div class="col text-center">`+s.num+`</div>
    <div class="col text-center">`+s.date+`</div>
</div>
`;
}
return res+"</div>";
}


function resultStock(){
	fetch("http://localhost:8080/casseautomatiche/gdo/stock/").then(function(response) {
        return response.json();
      }).then((resp)=>{
       
        document.getElementById("result").innerHTML=renderStock(resp);
    }).catch(err=>alert(err.message
    ));
}

function getDailyTackings(){
    fetch("http://localhost:8080/casseautomatiche/gdo/daily-takings").then(function(response) {
        return response.json();
      }).then((resp)=>{
        document.getElementById("result").innerHTML="<div class='p-5 m-5 shadow text-center'>"+resp+"</div>";
    }).catch(err=>alert(err.message
    ));
}



function renderProductOrDepartmentTaking(list, label){
    res =`<div class=" mt-4 p-5 border-1 border-primary shadow">
    <div class="row p-1 mb-3">
        <div class="col text-center"><b>ID</b></div>
        <div class="col text-center"><b>` + label + `</b></div>
        <div class="col text-center"><b>TOTAL</b></div>
        <div class="col text-center"><b>DATE</b></div>
    </div>
    `;
for(p of list){
res +=`
<div class="row p-1">
    <div class="col text-center">`+ p.id +`</div>
    <div class="col text-center">`+p.description+`</div>
    <div class="col text-center">`+p.total+`</div>
    <div class="col text-center">`+p.date+`</div>
</div>
`;
}
return res+"</div>";
}

function renderDepartmentTakingByYear(list){
    res =`<div class=" mt-4 p-5 border-1 border-primary shadow">
        <div class="row p-1 mb-3">
            <div class="col text-center"><b>ID</b></div>
            <div class="col text-center"><b>DEPARTMENT</b></div>
            <div class="col text-center"><b>TOTAL</b></div>
            <div class="col text-center"><b>YEAR</b></div>
        </div>
        `;
    for(p of list){
    res +=`
    <div class="row p-1">
        <div class="col text-center">`+ p.id +`</div>
        <div class="col text-center">`+p.description+`</div>
        <div class="col text-center">`+p.total+`</div>
        <div class="col text-center">`+p.year+`</div>
    </div>
    `;
    }
    return res+"</div>";
}

function tackingsByDateGroupByProduct(){
    let date = document.getElementById("date-pro").value;
    fetch("http://localhost:8080/casseautomatiche/gdo/takings4product/bydate/"+date).then(function(response) {
        return response.json();
      }).then((resp)=>{
        document.getElementById("result").innerHTML=renderProductOrDepartmentTaking(resp,"PRODUCT");
    }).catch(err=>alert(err.message));
}

function tackingsByDateGroupByDepartment(){
    let date = document.getElementById("date-depart").value;
    fetch("http://localhost:8080/casseautomatiche/gdo/takings4department/bydate/"+date).then(function(response) {
        return response.json();
      }).then((resp)=>{
        document.getElementById("result").innerHTML=renderProductOrDepartmentTaking(resp,"DEPARTMENT");
    }).catch(err=>alert(err.message ));
}

function tackingsByYearGroupByDepartment(){
	 let year = document.getElementById("year-depart").value;
	    fetch("http://localhost:8080/casseautomatiche/gdo/takings4department/byyear/"+year).then(function(response) {
	        return response.json();
	      }).then((resp)=>{
	        document.getElementById("result").innerHTML=renderDepartmentTakingByYear(resp);
	    }).catch(err=>alert(err.message));
}

</script>
<div class="p-5 m-5 ">
	<div class="row d-flex justify-content-center">
		<h1 >BENVENUTO NELL NOSTRE CASSE AUTOMATICHE</h1>
	</div>
	
	<div class="row d-flex justify-content-center">
		<div class="col-12"><button onCLick="resultStock()" class=" mx-4 my-1 btn btn-primary rounded px-4 py-2">AGGIORNA_STOCK_FINE_GIORNATA</button><br></div>
		<div class="col-12"><button onCLick="getDailyTackings()"  class=" mx-4 my-1 btn btn-primary rounded px-4 py-2"  >INCASSO_GIORNATA</button><br></div>
		<div class="col-12 d-flex align-items-center">
			<button onCLick="tackingsByDateGroupByProduct()"  class=" mx-4 my-1 btn btn-primary rounded px-4 py-2" >INCASSO__PER_ARTICOLO_DATA_UNA_GIORNATA</button>
			Seleziona una data: <input type="date" id="date-pro" class="input p-1 px-3 rounded ms-2">
		</div>
		<div class="col-12">
			<button onCLick="tackingsByDateGroupByDepartment()"  class=" mx-4 my-1 btn btn-primary rounded px-4 py-2" >INCASSO_PER_REPARTO_DATA_UNA_GIORNATA</button>
			Seleziona una data: <input type="date" id="date-depart" class="input p-1 px-3 rounded ms-2">
		</div>
		<div class="col-12">
			<button onCLick="tackingsByYearGroupByDepartment()"  class=" mx-4 my-1 btn btn-primary rounded px-4 py-2" >INCASSO_PER_REPARTO_DATO_UN_ANNO</button>
			Inserisci un anno: <input type="number" min="2000" value="2024" id="year-depart" class="input p-1 px-3 rounded ms-2">
		</div>
	</div>
	<div id="result" class="row d-flex justify-content-center">
		
	</div>

</div>
</body>
</html>