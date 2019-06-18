// Check session if session is active
$(window).on('load', checkSession);


//region modal
var modalid = ["#shift_add","#shift_edit","#shift_delete","#job_add","#job_edit","#job_delete","#employer_add","#employer_edit","#employer_delete"];


$(window).on("click", function(e) {
    for (i=0;i<modalid.length;i++) {
        if (e.target===$(modalid[i])[0]) {
            hideModal();
        }
    }
});
var hideModal = function(){
   for (i=0;i<modalid.length;i++) {
       $(modalid[i]).hide();
   }
};


var showactivity_add = function() {
    $("#shift_add").show();
    getJobList(joblist);
};
var showactivity_edit = function() {
    $("#shift_edit").show();
    getJobList(activity_edit_joblist);
};
var showactivity_delete = function() {
    $("#shift_delete").show();
    getActivityList(activitylist_delete);
};
var showjob_add = function() {
    $("#job_add").show();
    getEmployerList(company_job_add);
};
var showjob_edit = function() {
    $("#job_edit").show();
    getJobList(select_job_edit);
};
var showjob_delete = function() {
    $("#job_delete").show();
    getJobList(select_job_delete);
};
var showemployer_add = function() {
    $("#employer_add").show();
};
var showemployer_edit = function() {
    $("#employer_edit").show();
    getEmployerList(company_employer_edit);
};
var showemployer_delete = function() {
    $("#employer_delete").show();
    getEmployerList(company_employer_delete)
};


//endregion

//Dette er metoderne til tabellen
function selectShift(name) {


    var selector = document.getElementById(name);

    var allShift = findAllShift();
if (selector!=null){
    while(selector.hasChildNodes()){
        selector.removeChild(selector.firstChild);
    }
    selector.appendChild(document.createElement('option'));

    while(allShift.length > 0) {

        var currentShift = allShift.pop();
        var option = document.createElement('option');
        var name1 = currentShift[2].getDate() + '/' + (currentShift[2].getMonth()+1) + ' at ' + currentShift[2].getHours() + ':' + currentShift[2].getMinutes() + ' to ' + currentShift[3].getHours() + ':' + currentShift[3].getMinutes();
        option.appendChild(document.createTextNode(name1));
        option.value = currentShift[0];

        selector.appendChild(option);
    }
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

    if (selector!=null){
        while(selector.hasChildNodes()){
            selector.removeChild(selector.firstChild);
        }
        selector.appendChild(document.createElement('option'));

    for (var i = 0; i < findAllJobs().length; i++) {
        var currentJob = allJobs.pop();
        var option = document.createElement('option');
        var name1 = currentJob[2];
        option.appendChild(document.createTextNode(name1));
        option.value = currentJob[0];
        selector.appendChild(option);
        }
    }
}

function selectJobadress(name) {
    var selector = document.getElementById(name);
    var allJobadress = findAllJobaddress();

    if (selector!=null) {
    while(selector.hasChildNodes()){
        selector.removeChild(selector.firstChild);
    }
    selector.appendChild(document.createElement('option'))

    for (var i = 0; i < findAllJobaddress().length; i++) {
        var currentJobadress = allJobadress.pop();
        var option = document.createElement('option');
        var name1 = findJob(currentJobadress[4])[2];
        option.appendChild(document.createTextNode(name1));
        option.value = currentJobadress[4];
        selector.appendChild(option);
    }
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
        success : function(){
            alert("Created Activity");
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
    var userJson = $("#addEmployer input").serializeJSON();
    $.ajax({
        method: 'POST',
        url : "/MyHours/DBController/createEmployer",
        data : userJson,
        contentType: "application/json",
        success : function(){
            alert("Created Employer");
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
        success : function(){
            alert("Created Job");
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
        success : function(){
            alert("Edited Employer");
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
        success : function(){
            alert("Edited Job");
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

function deleteActivity() {
    event.preventDefault();

    //get
    var activityID = $("#deleteActivityID").val();

    //set
    $("#deleteActivityID").val(activityID);
    console.log(activityID);

    deleteActivityByID(activityID);
}

function deleteActivityByID(activityID){

    event.preventDefault();
    var json = JSON.stringify(activityID);
    $.ajax({
        method: 'DELETE',
        url : "/MyHours/DBController/deleteActivity",
        data : json,
        contentType: "application/json",
        success : function(){
            alert("Deleted Activity");
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to delete activity!")
        }
    });
    console.log(json);
}

function deleteEmployer() {
    event.preventDefault();

    var employerID = $("#deleteEmployerID").val();

    $("#deleteEmployerID").val(employerID);
    console.log(employerID);

    deleteEmployerByID(employerID);
}

function deleteEmployerByID(employerID){

    event.preventDefault();
    var json = JSON.stringify(employerID);
    $.ajax({
        method: 'DELETE',
        url : "/MyHours/DBController/deleteEmployer",
        data : json,
        contentType: "application/json",
        success : function(){
            alert("Deleted Employer");
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to delete Employer!")
        }
    });
    console.log(json);
}

function deleteJob() {
    event.preventDefault();

    var jobID = $("#deleteJobID").val();

    $("#deleteJobID").val(jobID);
    console.log(jobID);

    deleteJobByID(jobID);
}

function deleteJobByID(jobID){
    var json = JSON.stringify(jobID);

    $.ajax({
        method: 'DELETE',
        url : "/MyHours/DBController/deleteJob",
        data : json,
        contentType: "application/json",
        success : function(){
            alert("Deleted Job");
            console.log("Success!");
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
            console.log("Failed to delete Job!")
        }
    });
    console.log(json);
}
function checkSession() {
    $.ajax({
        method: 'POST',
        url: "MyHours/DBController/isSessionActive",
        contentType: "application/json",
        success : function (data) {
            if (data.toString() === "false") {
                window.location = "../index.html"
            }
        },
        error: function (jqXHR, text, error) {
            alert(jqXHR.status + text + error);
            console.log("Failed to check session!")
        }
    });
}

function generateUserHTML(job){
    var deleteId = job.id;

    console.log(job.id);

  //  console.log("user id "+deleteId);
    //console.log("user id" + user.id);

    return 	'<option>' +  +
        '</option>';
}

function getJobList(input) {
    $.ajax({
        method: "GET",
        url: "/MyHours/DBController/getJobList",
        dataType: "JSON",
        success: function(response) {
            var option = '';
            $.each(response, function(i) {
               /* console.log(response[i]);
                console.log("jobID: " + response[i]['jobID']);
                console.log("employerID: " + response[i]['employerID']);
                console.log("jobName: " + response[i]['jobName']);
                console.log("stdSalary: " + response[i]['stdSalary']);
*/

                option += '<option>' + response[i]['jobName'] +
                    '</option>';

            });
            switch (input) {
                case joblist:
                    $('#joblist').html(option);
                    break;

                case select_job_edit:
                    $('#select_job_edit').html(option);
                    getEmployerList(company_job_edit)
                    break;

                case activity_edit_joblist:
                    $('#activity_edit_joblist').html(option);
                    getActivityList(activitylist_edit);
                    break;

                case select_job_delete:
                    $('#select_job_delete').html(option);
                    break;
            }

        },
        error: function() {
            console.log("Error loading jobs");
        }
    });
}

function getActivityList(input) {
    $.ajax({
        method: "GET",
        url: "/MyHours/DBController/getActivityList",
        dataType: "JSON",
        success: function(response) {
            var option = '';
            $.each(response, function(i) {

                var start = response[i]['startingDateTime'];
                var end = response[i]['endingDateTime'];

                option += '<option>' + start + end + response[i]['activityValue'] +'</option>';

            });
            switch (input) {
                case activitylist_edit:
                    $('#activitylist_edit').html(option);
                    break;

                case activitylist_delete:
                    $('#activitylist_delete').html(option);
                    break;
        }
        },
        error: function() {
            console.log("Error loading activities");
        }
    });
}

//TODO Ser ikke ud til at den virker
function getEmployerList(input) {
    $.ajax({
        method: "GET",
        url: "/MyHours/DBController/getEmployerList",
        dataType: "JSON",
        success: function(response) {
            var option = '';
            $.each(response, function(i) {

                option += '<option>' + response[i]['name'] +
                    '</option>';

            });
            switch (input) {
                case company_job_add:
                    $('#company_job_add').html(option);
                    break;

                case company_job_edit:
                    $('#company_job_edit').html(option);
                    break;

                case company_employer_edit:
                    $('#company_employer_edit').html(option);
                    break;

                case company_employer_delete:
                    $('#company_employer_delete').html(option);
                    break;
            }
        },
        error: function() {
            console.log("Error loading employers");
        }
    });
}
function logOut() {
    $.ajax({
        method: 'POST',
        url: "MyHours/DBController/Logout",
        contentType: "application/json",
        success : function () {
            window.location = "../index.html"
        },
        error: function (jqXHR, text, error) {
            alert(jqXHR.status + text + error);
        }
    });
}

//alternativ luk modal, med style.display
/*var modal = document.getElementById('shift_add')
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}*/

//her er delene som fremkalder content.
function openInfo() {

    document.getElementById('popup_content').style.width = '100%';
    document.getElementById('popup_content').style.height = '80%';
    hoursOnWorkMounthly();
}
function closeInfo() {
    document.getElementById('popup_content').style.width = '0px';
    document.getElementById('popup_content').style.height = '0px';
}


//her er delene som ændre ved content.
function changeFunction(site) {
    //var elem = document.getElementById('content');
    if (site === 'shifts' && document.getElementById('myid1').checked){
        header('Shifts');
        left('shifts');
        //console.log(site);
    } else if (site === 'job' && document.getElementById('myid2').checked){
        header('Job');
        left('job');
        //console.log(site);
    } else if (site === 'workplace' && document.getElementById('myid3').checked){
        header('Workplace');
        left('workplace');
        //console.log(site);
    } else if(site === 'none' && document.getElementById('myid').checked) {
        header('');
        left('none');
        //console.log(site);
    }else{
        //checkMenu0();
        //closeInfo(); //FIXME Hvis disse 3 bliver aktiveret, vil grafen blive fjernet som forventet, men den klager over rekursion, så har valgt at udkommentere dem for performence skyld (ikke at jeg kunne mærke noget)
        //changeFunction('none');
        header('');
        left('none');
        //console.log(site+" close");
    }
}

function header(head) {
    document.getElementById('pop_top_head').innerHTML = head;
    if (head === ''){
        document.getElementById('pop_top2').style.backgroundColor = 'transparent';
    } else {
        document.getElementById('pop_top2').style.backgroundColor = 'rgb(52, 152, 219)';
    }
}



function left(type) {
    if (type === 'shifts') {
        shifts();
    }
    if (type === 'job'){
        job();
    }
    if (type === 'workplace') {
        workplace();
    }
    if (type === 'none'){
        none();
    }

}

function hoursOnWorkMounthly(){
    anychart.onDocumentReady(function() {


        var today = new Date();
        var mounth = String(today.getMonth());
        var year = today.getFullYear();

        var nrOfDays = daysInMonth(mounth,year);

        // set the data
        var data = {
            header: ["Date", "Hours"]
        };

        //her finder jeg alle de job som er forbundet med brugeren.
        var nrOfJobs = [];
        for (var loop = 0; loop < findAllJobs().length ;loop++) {
            nrOfJobs[loop] = findAllJobs()[loop][0];
        }

        mounth++;

        //laver dataset
        var dataSets = [];

        for (var l=0;l<nrOfJobs.length;l++) {
            var dataSet = anychart.data.set(data);
            for (i = 0; i < nrOfDays; i++) {
                var name = 'Den ' + (i + 1) + '. i ' + (mounth) + '.';
                dataSet.append([name, hoursOfWork()]);
            }
            dataSets[l] = dataSet;
        }




        // create the chart
        var chart = anychart.column();
        chart.animation(true);

        var serieSet = [];

        //laver dataen til serie sæt.

        for (var r = 0; r<dataSets.length; r++) {
            serieSet[r] = dataSets[r].mapAs({'x': 0, 'value': 1})
        }

        chart.yScale().stackMode('value');



        // add the data
        for (var c = 0; c<serieSet.length; c++){
            chart.column(serieSet[c]);
        }


        var i=0;
        // create a loop
        while (chart.getSeriesAt(i)){
            // rename each series
            chart.getSeriesAt(i).name(findJob(nrOfJobs[i])[2]);
            i++;
        }

        // set the chart title
        chart.title("Hours on work");

        // draw
        chart.container("graph");
        chart.draw();
    });
}
