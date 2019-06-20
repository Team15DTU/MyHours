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

/*
function selectJobs(name) {
    var selector = document.getElementById(name);
    $.ajax({
        method: "GET",
        url:"/MyHours/DBController/getJobList",
        dataType: "JSON",
        success: function (result) {
            anychart.onDocumentReady(function() {

                var today = new Date();
                var month = String(today.getMonth());
                var year = today.getFullYear();

                var nrOfDays = daysInMonth(month,year);

                var allJobs = [];

                $.each(result, function(i) {

                    var jobDetails = [result[i]['jobID'], result[i]['employerID'], result[i]['jobName'], result[i]['stdSalary'], new Date(), 'Insert activity name'];
                    allJobs.push(jobDetails);
                });

                if (selector!=null){
                    while(selector.hasChildNodes()){
                        selector.removeChild(selector.firstChild);
                    }
                    selector.appendChild(document.createElement('option'));

                    for (var i = 0; i < findJobArray.length; i++) {
                        var currentJob = allJobs.pop();
                        var option = document.createElement('option');
                        var name1 = currentJob[2];
                        option.appendChild(document.createTextNode(name1));
                        option.value = currentJob[0];
                        selector.appendChild(option);
                    }
                }

            });
        }
    });
}
*/

function none() {
    var table = document.getElementById('left_table');
    while(table.hasChildNodes()){
        table.removeChild(table.firstChild);
    }
}


//her er de metoder til  felterne som skal snakke med serveren

function lastPaycheck(nr) {
    return 2500;
}

function estimatePaycheck(nr) {
    return 1750;
}

function findShift(id) {


    if (id == 0) {
       var shift = [0,1,new Date('2019-06-27T12:00:00+01:00'), new Date('2019-06-27T16:00:04+01:00'), 5, 3];
        return shift;
    } else if (id == 1){
        var shift = [1,2,new Date('2019-06-28T10:00:08+01:00'), new Date('2019-06-28T20:00:16+01:00'), 10, 3];
        return shift;
    } else if (id == 2) {
        var shift = [2,3,new Date(), new Date(), 20, 3];
        return shift;
    } else {
        var shift = [3,0,new Date(), new Date(), 25, 3];
        return shift;
    }


    return 'shift';
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

    var userJson =  JSON.parse(JSON.stringify($("#addActivity").serializeJSON()));
    var test = $("#addActivity").serializeJSON();
    console.log(test);
    $("#addActivity").val(test);
    console.log(test);


    var parsedString = userJson.replace(/@\{(\w+)\}/g,function(match, group) {
        console.log(group);
        console.log(match);
        if (group.toString() === 'startingDateTime') {
            // TODO: Hej WassMann, kan du ikke se om nedenstående løsning virker?
            return new console.log(Date().toISOString());
        }
        else if (group.toString() === 'endingDateTime') {
            return new Date().toISOString.slice(0,19);
        } //and so on
    });
    //console.log(parsedString);

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
        url : "/MyHours/DBController/updateActivity",
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
        url : "/MyHours/DBController/updateEmployer",
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
        url : "/MyHours/DBController/updateJob",
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

function deleteActivity() {
    event.preventDefault();

    //get
    var activityID = $("#activitylist_delete").val();

    //set
    $("#activitylist_delete").val(activityID);

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

    var name = $("#company_employer_delete").val();

    $("#company_employer_delete").val(name);
    console.log(name);

    deleteEmployerByName(name);
}

function deleteEmployerByName(name){

    event.preventDefault();
    var json = JSON.stringify(name);
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

    var jobName = $("#select_job_delete").val();

    $("#select_job_delete").val(jobName);
    console.log(jobName);

    deleteJobByName(jobName);
}

function deleteJobByName(jobName){
    var json = JSON.stringify(jobName);

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

function updateGraf(choice) {
    $.ajax({
        method: "GET",
        url: "/MyHours/DBController/getJobList",
        dataType: "JSON",
        success: function (result) {
            anychart.onDocumentReady(function () {

                $('#graph').empty();

                var today = new Date();
                var month = String(today.getMonth());
                var year = today.getFullYear();

                var nrOfDays = daysInMonth(month, year);

                var findJobArray = [];

                $.each(result, function (i) {
                    var jobDetails = [result[i]['jobID'], result[i]['employerID'], result[i]['jobName'], result[i]['stdSalary'], 'Insert activity name'];
                    findJobArray.push(jobDetails);
                });

                // set the data
                var data = {
                    header: ["Date", "Hours"]
                };

                //her finder jeg alle de job som er forbundet med brugeren.
                var nrOfJobs = [];
                //TODO: Her skal vi erstatte vores data. findAllJobs finder alle jobs og den resterende funktion laver grafen
                for (var loop = 0; loop < findJobArray.length; loop++) {
                    nrOfJobs[loop] = findJobArray[loop][0];
                }

                month++;

                        //laver dataset
                        var dataSets = [];

                        for (var l = 0; l < nrOfJobs.length; l++) {
                            var dataSet = anychart.data.set(data);
                            for (i = 0; i < nrOfDays; i++) {
                                var name = 'Den ' + (i + 1) + '. i ' + (month) + '.';
                                dataSet.append([findJobArray,hoursOfWork()]);
                            }
                            dataSets[l] = dataSet;
                        }
                // create the chart
                var chart = anychart.column();
                chart.animation(true);

                var serieSet = [];

                //laver dataen til serie sæt.

                for (var r = 0; r < dataSets.length; r++) {
                    serieSet[r] = dataSets[r].mapAs({'x': 0, 'value': 1})
                }

                chart.yScale().stackMode('value');

                // add the data
                for (var c = 0; c < serieSet.length; c++) {
                    chart.column(serieSet[c]);
                }


                var i = 0;
                // create a loop
                while (chart.getSeriesAt(i)) {
                    // rename each series
                    chart.getSeriesAt(i).name(findJob(nrOfJobs[i])[2]);
                    i++;
                }

                // set the chart title
                chart.title("Hours of activities");

                // draw
                chart.container("graph");
                chart.draw();
            });

            switch (choice) {
                case "shiftInfo":
                    $.ajax({
                        method: "GET",
                        url: "/MyHours/DBController/getActivityList",
                        dataType: "JSON",
                        success: function (resulte) {
                            var table = document.getElementById('left_table');
                            while (table.hasChildNodes()) {
                                table.removeChild(table.firstChild);
                            }

                            var vagt = 'Starting';
                            var job = 'Ending';
                            var salary = 'Activity value';

                            var row = table.insertRow(0);
                            row.insertCell(0).innerHTML = vagt.bold();
                            row.insertCell(1).innerHTML = job.bold();
                            row.insertCell(2).innerHTML = salary.bold();
                            var i;

                            //TODO INSERT REAL ACTIVITY INFORMATION
                            for (i = 0; i < 3; i++) {
                                var row2 = table.insertRow(i + 1);

                                var shiftStart = findShift(i)[2];
                                var shiftEnd = findShift(i)[3];


                                var shiftStartString = shiftStart.getDate() + '/' + (shiftStart.getMonth() + 1) + ' ' + with_leading_zeros(shiftStart.getHours()) + ':' + with_leading_zeros(shiftStart.getMinutes()) + ' - ' + with_leading_zeros(shiftEnd.getHours()) + ':' + with_leading_zeros(shiftEnd.getMinutes());


                                row2.insertCell(0).innerHTML = resulte[i]['startingDateTime'];
                                row2.insertCell(1).innerHTML = resulte[i]['endingDateTime'];
                                row2.insertCell(2).innerHTML = resulte[i]['activityValue'];
                            }
                        }
                    })
                    break;

                case "jobInfo":
                    $.ajax({
                        method: "GET",
                        url: "/MyHours/DBController/getEmployerList",
                        dataType: "JSON",
                        success: function (resulte) {
                            var table = document.getElementById('left_table');
                            while (table.hasChildNodes()) {
                                table.removeChild(table.firstChild);
                            }
                            var firm = 'Employer';
                            var lastpay = 'Job';
                            var recivepay = 'Hourly salary';


                            var row = table.insertRow(0);
                            row.insertCell(0).innerHTML = firm.bold();
                            row.insertCell(1).innerHTML = lastpay.bold();
                            row.insertCell(2).innerHTML = recivepay.bold();
                            var i;

                            $.each(result, function (k) {
                                //new Date();
                                //'Insert activity name';
                                var row2 = table.insertRow(k + 1);
                                row2.insertCell(0).innerHTML = resulte[k]['name'];//findJob(i)[2];
                                row2.insertCell(1).innerHTML = result[k]['jobName']; //lastPaycheck(findJob(k)[0])+' Kr.';
                                row2.insertCell(2).innerHTML = result[k]['stdSalary'];
                            });
                        }

                    })
                    break;




                case "employerInfo":

                    $.ajax({
                        method: "GET",
                        url: "/MyHours/DBController/getEmployerList",
                        dataType: "JSON",
                        success: function (result) {
                            var table = document.getElementById('left_table');
                            while (table.hasChildNodes()) {
                                table.removeChild(table.firstChild);
                            }

                            var firm = 'Employer';

                            var row = table.insertRow(0);
                            row.insertCell(0).innerHTML = firm.bold();
                            var i;

                            for (i = 0; i < 3; i++) {
                                var row2 = table.insertRow(i + 1);
                                row2.insertCell(0).innerHTML = result[i]['name'];
                            }
                        }
                    })
            }
        }
    })
}


function getJobList(input) {
    $.ajax({
        method: "GET",
        url: "/MyHours/DBController/getJobList",
        dataType: "JSON",
        success: function(response) {
            var option = '';
            $.each(response, function(i) {
                /*
                console.log(response[i]);
                console.log(response[i]['jobID']);
                console.log(response[i]['employerID']);
                console.log(response[i]['jobName']);
                console.log(response[i]['stdSalary']);
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
                    getEmployerList(company_job_edit);
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


//her er delene som fremkalder content.
function openInfo(openBox) {
    $('#popup_content').show();
    updateGraf(openBox);
}

function closeInfo() {
    $('#popup_content').hide();
}


//her er delene som ændre ved content.
function changeFunction(site) {
    if (site === 'shifts' && $('#myid1').prop('checked')) {
        header('Shifts');
        checkMenu1();
        openInfo('shiftInfo');
        //console.log(site);
    } else if (site === 'job' && $('#myid2').prop('checked')) {
        header('Job');
        checkMenu2();
        openInfo('jobInfo');
        //console.log(site);
    } else if (site === 'workplace' && $('#myid3').prop('checked')) {
        header('Workplace');
        checkMenu3();
        openInfo('employerInfo');
        //console.log(site);
    } else if (site === 'none' && $('#myid').prop('checked')) {
        header('');
        none();
        checkMenu0();
        closeInfo();
        //console.log(site);
    } else {
        header('');
        none();
        checkMenu0();
        closeInfo();
        //console.log(site+" close");
    }
}

function header(head) {
    $('#pop_top_head')[0].innerHTML = head;
    if (head === '') {
        $('#pop_top2').hide();
    } else {
        $('#pop_top2').show();
    }
}


