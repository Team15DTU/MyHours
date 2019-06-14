//Dette metoderne til tabellen

function selectShift() {


    var selector = document.getElementById('select');

    var allShift = findAllShift();

    for (var i = 0; i < 3; i++) {

        var currentShift = allShift.pop();
        var name = currentShift[5];
        var option = document.createElement('option');
        option.appendChild(document.createTextNode(name));
        option.value = currentShift[0];
        option.onclick = shiftEditSubmitChange(currentShift[0]);
        selector.appendChild(option);
    }
}

function shiftEditSubmitChange(value) {
    return document.getElementById('shift_edit_submit').value = value;
}

function shifts() {

    var table = document.getElementById('left_table');
    while(table.hasChildNodes()){
        table.removeChild(table.firstChild);
    }

    var vagt = 'Vagt';
    var job = 'Job';
    var salary = 'Løn';

    var row = table.insertRow(0);
    row.insertCell(0).innerHTML = vagt.bold();
    row.insertCell(1).innerHTML = job.bold();
    row.insertCell(2).innerHTML = salary.bold();
    var i;

    for (i = 0; i < 3; i++){
        var row2 = table.insertRow(i+1);
        row2.insertCell(0).innerHTML = findShift(i);
        row2.insertCell(1).innerHTML = findWork(i);
        row2.insertCell(2).innerHTML = findPaycheck(i);
    }

}

function job() {

    var table = document.getElementById('left_table');
    while(table.hasChildNodes()){
        table.removeChild(table.firstChild);
    }
    var firm = 'Firma';
    var lastpay = 'Sidste udbetaling';
    var recivepay = 'Optjent løn';


    var row = table.insertRow(0);
    row.insertCell(0).innerHTML = firm.bold();
    row.insertCell(1).innerHTML = lastpay.bold();
    row.insertCell(2).innerHTML = recivepay.bold();
    var i;

    for (i = 0; i < 1; i++){
        var row2 = table.insertRow(i+1);
        row2.insertCell(0).innerHTML = findCompany(i);
        row2.insertCell(1).innerHTML = lastPaycheck(i)+' Kr.';
        row2.insertCell(2).innerHTML = estimatePaycheck(i)+' Kr.';
    }
}

function workplace() {
    var table = document.getElementById('left_table');
    while(table.hasChildNodes()){
        table.removeChild(table.firstChild);
    }

    var firm = 'Firma';

    var row = table.insertRow(0);
    row.insertCell(0).innerHTML = firm.bold();
    var i;

    for (i = 0; i < 3; i++){
        var row2 = table.insertRow(i+1);
        row2.insertCell(0).innerHTML = findCompany(i);
    }
}

function none() {
    var table = document.getElementById('left_table');
    while(table.hasChildNodes()){
        table.removeChild(table.firstChild);
    }
}


//her er de metoder til  felterne som skal snakke med serveren

function findCompany(nr) {
    return 'irma'
}

function findNrOfCompanys() {
    return 3;
}

function findCompanyId(nr) {
    if (nr == 0){return 1;}
    if (nr == 1){return 2;}
    else {return 3}
}

function lastPaycheck(nr) {
    return 2500;
}

function estimatePaycheck(nr) {
    return 1750;
}

function findShift(nr) {
    return '16-20';
}

function findAllShift(nr) {
    var allShift = [];

    for (var i = 0; i < 3; i++) {
        var endDate = new Date();
        endDate.setDate(endDate.getDate()+i);
        var startDate = new Date();
        startDate.setDate(startDate.getDate()-i);

        var shift = [id = i, jobid = 2, start = startDate, end = endDate, breaks = 30, jobname = findWork(i)]
        allShift.push(shift);
    }


    return allShift;

}


function findWork(nr) {
    if (nr == 0) {return 'kvikly'}
    else if (nr == 1) {return 'fakta'}
    else if (nr == 2) {return 'irma'}
    else if (nr == 3) {return '#VektorLife'}
}



function findPaycheck(nr) {
    return '444 kr.'
}


//her er graferne

function daysInMonth (month, year) {
    return new Date(year, month, 0).getDate();
}

function hoursOfWork () {

    return Math.random()*10;
}




// Show only one item at a time in the menu
var checkMenu0 = function() {
    //$("#myid").attr("checked");
    $("#myid1").prop("checked", false);
    $("#myid2").prop("checked", false);
    $("#myid3").prop("checked", false);
};

var checkMenu1 = function() {
    //$("#myid").attr("checked");
    $("#myid").prop("checked", false);
    $("#myid2").prop("checked", false);
    $("#myid3").prop("checked", false);
};
var checkMenu2 = function() {
    //$("#myid").attr("checked");
    $("#myid1").prop("checked", false);
    $("#myid").prop("checked", false);
    $("#myid3").prop("checked", false);
};

var checkMenu3 = function() {
    //$("#myid").attr("checked");
    $("#myid1").prop("checked", false);
    $("#myid2").prop("checked", false);
    $("#myid").prop("checked", false);
};
