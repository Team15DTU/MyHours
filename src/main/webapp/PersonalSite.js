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
};
var showactivity_edit = function() {
    $("#shift_edit").show();
};
var showactivity_delete = function() {
    $("#shift_delete").show();
};
var showjob_add = function() {
    $("#job_add").show();
};
var showjob_edit = function() {
    $("#job_edit").show();
};
var showjob_delete = function() {
    $("#job_delete").show();
};
var showemployer_add = function() {
    $("#employer_add").show();
};
var showemployer_edit = function() {
    $("#employer_edit").show();
};
var showemployer_delete = function() {
    $("#employer_delete").show();
};


//endregion


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

    var userJson =  $("#addActivity").serializeJSON();
    $.ajax({
        method: 'POST',
        url : "/MyHours/ArrayDBController/createActivity",
        data : userJson,
        contentType: "application/json",
        success : function(){
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
        }
    });
}

function addEmployer(){

    event.preventDefault();
    var userJson = $("#addEmployer input").serializeJSON();
    $.ajax({
        method: 'POST',
        url : "/MyHours/ArrayDBController/createEmployer",
        data : userJson,
        contentType: "application/json",
        success : function(){
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
        }
    });
}

function addJob(){

    event.preventDefault();
    var userJson = $("#addJob").serializeJSON();
    $.ajax({
        method: 'POST',
        url : "/MyHours/ArrayDBController/createJob",
        data : userJson,
        contentType: "application/json",
        success : function(){
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
        }
    });
}
function editActivity(){

    event.preventDefault();
    var userJson = $("#editActivity").serializeJSON();
    $.ajax({
        method: 'PUT',
        url : "/MyHours/ArrayDBController/updateActivity",
        data : userJson,
        contentType: "application/json",
        success : function(){
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
        }
    });
}

function editEmployer(){

    event.preventDefault();
    var userJson = $("#editEmployer").serializeJSON();
    $.ajax({
        method: 'PUT',
        url : "/MyHours/ArrayDBController/updateEmployer",
        data : userJson,
        contentType: "application/json",
        success : function(){
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
        }
    });
}

function editJob(){

    event.preventDefault();
    var userJson = $("#editJob").serializeJSON();
    $.ajax({
        method: 'PUT',
        url : "/MyHours/ArrayDBController/updateJob",
        data : userJson,
        contentType: "application/json",
        success : function(){
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
        }
    });
}

function deleteActivity() {
    event.preventDefault();

    //get
    var activityID = $("#activitylist_delete").val();

    //set
    $("#activitylist_delete").val(activityID);

    deleteActivityByID(activityID);
}

function deleteActivityByID(activityID){

    event.preventDefault();
    $.ajax({
        method: 'DELETE',
        url : "/MyHours/ArrayDBController/deleteActivity/" + activityID,
        contentType: "application/json",
        success : function(){
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
        }
    });
}

function deleteEmployer() {
    event.preventDefault();

    var employerID = $("#company_employer_delete").val();

    $("#company_employer_delete").val(employerID);

    deleteEmployerByID(employerID);
}

function deleteEmployerByID(employerID){

    event.preventDefault();
    $.ajax({
        type: 'DELETE',
        url : "/MyHours/ArrayDBController/deleteEmployer/" + employerID,
        contentType: "application/json",
        success : function(){
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
        }
    });
}

function deleteJob() {
    event.preventDefault();

    var jobName = $("#select_job_delete").val();

    $("#select_job_delete").val(jobName);

    deleteJobByName(jobName);
}

function deleteJobByName(jobName){

    $.ajax({
        method: 'DELETE',
        url : "/MyHours/ArrayDBController/deleteJob/" + jobName,
        contentType: "application/json",
        success : function(){
        },
        error: function(jqXHR, text, error){
            alert(jqXHR.status + text + error);
        }
    });
}
function checkSession() {
    $.ajax({
        method: 'POST',
        url: "MyHours/ArrayDBController/isSessionActive",
        contentType: "application/json",
        success : function (data) {
            if (data.toString() === "false") {
                window.location = "../index.html"
            }
        },
        error: function (jqXHR, text, error) {
            alert(jqXHR.status + text + error);
        }
    });
}

function updateGraf(choice) {
    $.ajax({
        method: "GET",
        url: "/MyHours/ArrayDBController/getActivityList",
        dataType: "JSON",
        success: function (result) {
            anychart.onDocumentReady(function () {

                $('#graph').empty();

                var today = new Date();
                var month = String(today.getMonth());
                var year = today.getFullYear();

                var nrOfDays = daysInMonth(month, year);

                var findJobArray = [];

                var shiftArray = [];

                $.each(result, function (i) {
                    var jobDetails = [result[i]['activityID'], result[i]['startingDateTime'], result[i]['endingDateTime']];
                    findJobArray.push(jobDetails);

                    //calculate shift time in hours
                    var secondTime =findJobArray[i][2].split("T").pop().split(":");
                    var firstTime =findJobArray[i][1].split("T").pop().split(":");
                    var secondTimeInMin = ((secondTime[0]*60)+ +secondTime[1])/60;
                    var firstTimeInMin = ((firstTime[0]*60)+ +firstTime[1])/60;

                    shiftArray[i] = secondTimeInMin-firstTimeInMin;
                });

                // set the data
                var data = {
                    header: ["Date", "Hours"]
                };

                //her finder jeg alle de job som er forbundet med brugerene.
                var nrOfJobs = [];
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
                        dataSet.append([findJobArray], shiftArray[i]);

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
                chart.title("Hours of activities a month");

                // draw
                chart.legend(true);
                chart.container("graph");
                chart.draw();
            });

            switch (choice) {
                case "shiftInfo":
                    $.ajax({
                        method: "GET",
                        url: "/MyHours/ArrayDBController/getActivityList",
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

                            $.each(resulte, function (i) {
                                var row2 = table.insertRow(i + 1);

                                row2.insertCell(0).innerHTML = resulte[i]['startingDateTime'].replace("T" , " ");
                                row2.insertCell(1).innerHTML = resulte[i]['endingDateTime'].replace("T" , " ");
                                row2.insertCell(2).innerHTML = resulte[i]['activityValue'];
                            })
                        }
                    });
                    break;

                case "jobInfo":
                    $.ajax({
                        method: "GET",
                        url: "/MyHours/ArrayDBController/getEmployerList",
                        dataType: "JSON",
                        success: function (response) {
                            $.each(response, function(i) {
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
                                $.each(result, function (k) {
                                    //new Date();
                                    //'Insert activity name';
                                    var row2 = table.insertRow(k + 1);
                                    if (result[k]['employerID']===response[i]['employerID']){
                                        var employerName = response[i]['name'];
                                        var employerName = response[i]['name']
                                    }
                                    //console.log(employerName)
                                    row2.insertCell(0).innerHTML = employerName;//findJob(i)[2];
                                    row2.insertCell(1).innerHTML = result[k]['jobName']; //lastPaycheck(findJob(k)[0])+' Kr.';
                                    row2.insertCell(2).innerHTML = result[k]['stdSalary'];
                                });
                            })
                        }

                    });
                    break;




                case "employerInfo":
                    $.ajax({
                        method: "GET",
                        url: "/MyHours/ArrayDBController/getEmployerList",
                        dataType: "JSON",
                        success: function (resulte) {
                            var table = document.getElementById('left_table');
                            while (table.hasChildNodes()) {
                                table.removeChild(table.firstChild);
                            }

                            var firm = 'Employer';

                            var row = table.insertRow(0);
                            row.insertCell(0).innerHTML = firm.bold();

                            $.each(resulte, function (i) {
                                var row2 = table.insertRow(i + 1);

                                row2.insertCell(0).innerHTML = resulte[i]['name'];
                            })
                        }
                    });
                    break;

            }
        }
    })
}


function getJobList(input) {
    $.ajax({
        method: "GET",
        url: "/MyHours/ArrayDBController/getJobList",
        dataType: "JSON",
        success: function(response) {
            var option = '';
            $.each(response, function(i) {
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
        url: "/MyHours/ArrayDBController/getActivityList",
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
        }
    });
}


function getEmployerList(input) {
    $.ajax({
        method: "GET",
        url: "/MyHours/ArrayDBController/getEmployerList",
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
        }
    });
}
function logOut() {
    $.ajax({
        method: 'POST',
        url: "MyHours/ArrayDBController/Logout",
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
        header('Activities');
        checkMenu1();
        openInfo('shiftInfo');
    } else if (site === 'job' && $('#myid2').prop('checked')) {
        header('Job');
        checkMenu2();
        openInfo('jobInfo');
    } else if (site === 'workplace' && $('#myid3').prop('checked')) {
        header('Employer');
        checkMenu3();
        openInfo('employerInfo');
    } else if (site === 'none' && $('#myid').prop('checked')) {
        header('');
        none();
        checkMenu0();
        closeInfo();
    } else {
        header('');
        none();
        checkMenu0();
        closeInfo();
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


