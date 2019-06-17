//Dette metoderne til tabellen

function selectShift(name) {


    var selector = document.getElementById(name);

    var allShift = findAllShift();

    while(selector.hasChildNodes()){
        selector.removeChild(selector.firstChild);
    }
    selector.appendChild(document.createElement('option'))

    while(allShift.length > 0) {

        var currentShift = allShift.pop();
        var option = document.createElement('option');
        var name = currentShift[2].getDate() + '/' + (currentShift[2].getMonth()+1) + ' at ' + currentShift[2].getHours() + ':' + currentShift[2].getMinutes() + ' to ' + currentShift[3].getHours() + ':' + currentShift[3].getMinutes();
        option.appendChild(document.createTextNode(name));
        option.value = currentShift[0];

        selector.appendChild(option);
    }
}


function currentShiftToEdit() {

    var mylist = document.getElementById('select');
    var shift = findShift(mylist.options[mylist.selectedIndex].value);



    document.getElementById('shiftEditInfo').innerHTML = ' i ' + findJob(shift[1])[2];
/*
    var shiftEditStart = shift[2].getFullYear() + '-' + (shift[2].getMonth()-1) + '-' + shift[2].getDate() + 'T' + shift[2].getHours + ':' + shift[2].getMinutes;

    Begge disse 2 variabler skal ændre value til en html "dateTime-local" værdi.

    document.getElementById('shift_edit_start').value = shiftEditStart;
    document.getElementById('shift_edit_end').value = shiftEditStart;
*/
    document.getElementById('shift_edit_break').value = shift[4];

}

function deleteShiftFromHTTP() {
    var mylist = document.getElementById('select3');
    deleteShift(mylist.options[mylist.selectedIndex].value);
}

function deleteJobFromHTTP() {
    var mylist = document.getElementById('select_job_delete');
    deleteJob(mylist.options[mylist.selectedIndex].value);
}



function selectJobs(name) {
    var selector = document.getElementById(name);
    var allJobs = findAllJobs();

    while(selector.hasChildNodes()){
        selector.removeChild(selector.firstChild);
    }
    selector.appendChild(document.createElement('option'))

    for (var i = 0; i < findAllJobs().length; i++) {
        var currentJob = allJobs.pop();
        var option = document.createElement('option');
        var name = currentJob[2];
        option.appendChild(document.createTextNode(name));
        option.value = currentJob[0];
        selector.appendChild(option);

    }
}

function selectJobadress(name) {
    var selector = document.getElementById(name);
    var allJobadress = findAllJobaddress();

    while(selector.hasChildNodes()){
        selector.removeChild(selector.firstChild);
    }
    selector.appendChild(document.createElement('option'))

    for (var i = 0; i < findAllJobaddress().length; i++) {
        var currentJobadress = allJobadress.pop();
        var option = document.createElement('option');
        var name = findJob(currentJobadress[4])[2];
        option.appendChild(document.createTextNode(name));
        option.value = currentJobadress[4];
        selector.appendChild(option);
    }
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

        var shiftStart = findShift(i)[2];
        var shiftEnd = findShift(i)[3];


        var shiftStartString = shiftStart.getDate() + '/' + (shiftStart.getMonth()+1) + ' ' + with_leading_zeros(shiftStart.getHours()) + ':' + with_leading_zeros(shiftStart.getMinutes()) + ' - ' +with_leading_zeros(shiftEnd.getHours()) + ':' + with_leading_zeros(shiftEnd.getMinutes());


        row2.insertCell(0).innerHTML = shiftStartString;
        row2.insertCell(1).innerHTML = findJob(i)[2];
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
        row2.insertCell(0).innerHTML = findJob(i)[2];
        row2.insertCell(1).innerHTML = lastPaycheck(findJob(i)[0])+' Kr.';
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
        row2.insertCell(0).innerHTML = findJob(i)[2];
    }
}

function none() {
    var table = document.getElementById('left_table');
    while(table.hasChildNodes()){
        table.removeChild(table.firstChild);
    }
}


//her er de metoder til  felterne som skal snakke med serveren


function findNrOfCompanys() {
    return 3;
}


function lastPaycheck(nr) {
    return 2500;
}

function estimatePaycheck(nr) {
    return 1750;
}

function findShift(id) {


    if (id == 0) {
       var shift = [0,1,new Date('2019-06-27T12:00:00+01:00'), new Date('2019-06-27T16:00:04+01:00'), 5, 3]
        return shift;
    } else if (id == 1){
        var shift = [1,2,new Date('2019-06-28T10:00:08+01:00'), new Date('2019-06-28T20:00:16+01:00'), 10, 3]
        return shift;
    } else if (id == 2) {
        var shift = [2,3,new Date(), new Date(), 20, 3]
        return shift;
    } else {
        var shift = [3,0,new Date(), new Date(), 25, 3]
        return shift;
    }


    return 'shift';
}

function findAllShift(nr) {
    var allShift = [];

    for (var i = 0; i < 5; i++) {
        var endDate = new Date();
        var startDate = new Date();

        var shift = [i, 0, startDate, endDate, 30, findJob(i)[2]]
        allShift.push(shift);
    }


    return allShift;

}

function findAllJobaddress(nr) {
    var allCompany = [];

    for (var i = 0; i < 3; i++) {
        var streetname = 'Elektro vej';
        var company = [streetname, i, 2800, 'Lyngby', findJob(i)[0]];
        allCompany.push(company);
    }


    return allCompany;
}

function findJob(userid) {

    if (userid == 0){
        var job = [0, 66, 'Kvikly', 110, new Date(), 'user'];
    }
    else if (userid == 1) {
        var job = [1, 66, 'Fakta', 115, new Date(), 'user'];
    }
    else if (userid == 2){
        var job = [2, 66, 'Irma', 110, new Date(), 'user'];
    } else {
        var job = [2, 66, 'Studie starten', 10, new Date(), 'user'];
    }
    return job;

}


function findWorkplace(nr) {
    if (nr == 0) {return 'kvikly'}
    else if (nr == 1) {return 'fakta'}
    else if (nr == 2) {return 'irma'}
    else if (nr == 3) {return 'Vektor'}
}

function findAllJobs(userID) {

    var jobs = [];
    var kvikly = [0, 66, 'Kvikly', 110, new Date(), 'Flaske dreng'];
    var fakta = [1, 66, 'Fakta', 115, new Date(), 'Kasse assistent'];
    var irma = [2, 66, 'Irma', 110, new Date(), 'service medarbejder'];
    var studieStarten = [3, 66, 'Studie starten', 115, new Date(), 'Vektor'];

    jobs.push(kvikly);
    jobs.push(fakta);
    jobs.push(irma);
    jobs.push(studieStarten);

    return jobs;
}


function deleteShift(shiftID) {

}



function findPaycheck(nr) {
    return '444 kr.'
}


//her er graferne

function hoursOfWork () {

    return Math.random()*10;
}

function daysInMonth (month, year) {
    return new Date(year, month, 0).getDate();
}


//til at få 0 foran tal mindre ind 10
function with_leading_zeros(dt)
{
    return (dt < 10 ? '0' : '') + dt;
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

function addActivity(){

    event.preventDefault();
    var userJson = $("#addActivity").serializeJSON();
    $.ajax({
        method: 'POST',
        url : "/MyHours/DBController/createActivity",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to add activity!")
        }
    });
    console.log(userJson);
}

function addEmployer(){

    event.preventDefault();
    var userJson = $("#addEmployer").serializeJSON();
    $.ajax({
        method: 'POST',
        url : "/MyHours/DBController/createEmployer",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to add Employer!")
        }
    });
    console.log(userJson);
}

function addJob(){

    event.preventDefault();
    var userJson = $("#addJob").serializeJSON();
    $.ajax({
        method: 'POST',
        url : "/MyHours/DBController/createJob",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to add Job!")
        }
    });
    console.log(userJson);
}
function editActivity(){

    event.preventDefault();
    var userJson = $("#editActivity").serializeJSON();
    $.ajax({
        method: 'PUT',
        url : "/MyHours/DBController/editActivity",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to edit activity!")
        }
    });
    console.log(userJson);
}

function editEmployer(){

    event.preventDefault();
    var userJson = $("#editEmployer").serializeJSON();
    $.ajax({
        method: 'PUT',
        url : "/MyHours/DBController/editEmployer",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to edit Employer!")
        }
    });
    console.log(userJson);
}

function editJob(){

    event.preventDefault();
    var userJson = $("#editJob").serializeJSON();
    $.ajax({
        method: 'PUT',
        url : "/MyHours/DBController/editJob",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to edit Job!")
        }
    });
    console.log(userJson);
}

function editUser(){

    event.preventDefault();
    var userJson = $("").serializeJSON();
    $.ajax({
        method: 'PUT',
        url : "/MyHours/DBController/editUser",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to edit User!")
        }
    });
    console.log(userJson);
}

function deleteActivity(){

    event.preventDefault();
    var userJson = $("#deleteActivity").serializeJSON();
    $.ajax({
        method: 'DELETE',
        url : "/MyHours/DBController/",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to delete activity!")
        }
    });
    console.log(userJson);
}

function deleteEmployer(){

    event.preventDefault();
    var userJson = $("#deleteEmployer").serializeJSON();
    $.ajax({
        method: 'DELETE',
        url : "/MyHours/DBController/",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to delete Employer!")
        }
    });
    console.log(userJson);
}

function deleteJob(){

    event.preventDefault();
    var userJson = $("#deleteJob").serializeJSON();
    $.ajax({
        method: 'DELETE',
        url : "/MyHours/DBController/",
        data : userJson,
        contentType: "application/json",
        success : function(data){
            alert(data);
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to delete Job!")
        }
    });
    console.log(userJson);
}

function generateJobHTML(data){

    console.log("job id" + data.name);

    return 	'<tr>' + '<td>' + data.name + '</td>' + '</tr>';
}

function getJobList() {
    $.ajax({
        method: "GET",
        url: "/UserService/DBController/getJobList",
        dataType: "JSON",
        success: function(response) {
            $.each(response, function(i, job) {
                $("#select2").append(generateJobHTML(job));

            });
        },
        error: function() {
            console.log("Error loading jobs");
        }
    });
}

function getActivityList() {
    $.ajax({
        method: "GET",
        url: "/UserService/DBController/getActivityList",
        dataType: "JSON",
        success: function(response) {
            $.each(response, function(i, activity) {
                $("#select").append(generateJobHTML(activity));

            });
        },
        error: function() {
            console.log("Error loading activities");
        }
    });
}

function getEmployerList() {
    $.ajax({
        method: "GET",
        url: "/UserService/DBController/getEmployerList",
        dataType: "JSON",
        success: function(response) {
            $.each(response, function(i, employer) {
                $("#select").append(generateJobHTML(employer));

            });
        },
        error: function() {
            console.log("Error loading employers");
        }
    });
}