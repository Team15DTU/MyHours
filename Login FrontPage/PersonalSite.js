

function shifts() {

    var table = document.getElementById('left_table');
    while(table.hasChildNodes()){
        table.removeChild(table.firstChild);
    }

    var row = table.insertRow(0);
    row.insertCell(0).innerHTML = 'vagt';
    row.insertCell(1).innerHTML = 'job';
    row.insertCell(2).innerHTML = 'løn';
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

    var row = table.insertRow(0);
    row.insertCell(0).innerHTML = 'firma';
    row.insertCell(1).innerHTML = 'Sidste udbetaling';
    row.insertCell(2).innerHTML = 'Optjent løn';
    var i;

    for (i = 0; i < 1; i++){
        var row2 = table.insertRow(i+1);
        row2.insertCell(0).innerHTML = findCompany(i);
        row2.insertCell(1).innerHTML = lastPaycheck(i);
        row2.insertCell(2).innerHTML = estimatePaycheck(i);
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