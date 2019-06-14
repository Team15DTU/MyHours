//Dette metoderne til tabellen

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
    var firm = 'Firm';
    var lastpay = 'Last salary';
    var recivepay = 'Estimated salary';


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

// her er de metoder som bliver brugt til at laver graferne, sammen med "chart.
