
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
        row2.insertCell(1).innerHTML = lastPaycheck(i);
        row2.insertCell(2).innerHTML = estimatePaycheck(i);
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



function findCompany(nr) {
    return 'irma'
}

function lastPaycheck(nr) {
    return '2500 kr.'
}

function estimatePaycheck(nr) {
    return '1750 kr.'
}

function findShift(nr) {
    return '16-20';

}

function findWork(nr) {
    return 'irma';

}

function findPaycheck(nr) {
    return '444 kr.'
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
