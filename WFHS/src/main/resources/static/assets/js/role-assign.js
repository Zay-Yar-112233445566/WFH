$(document).ready(function () {
        
        //Applicant Register
        $('#divisionSelectApplicant').change(function () {
            var divisionName = $(this).val();

            // Make an AJAX call to fetch departments based on the selected division
            $.ajax({
                url: '/api/departmentName',
                type: 'GET',
                data: {divisionName: divisionName},
                success: function (data) {
                    // Clear existing options in the Department dropdown
                    $('#departmentSelectApplicant').empty();

                    // Add a default option
                    $('#departmentSelectApplicant').append($('<option>', {
                        value: '',
                        text: '----- Select Department -----'
                    }));

                    // Add new options based on the fetched departments
                    $.each(data, function (index, department) {
                        $('#departmentSelectApplicant').append($('<option>', {
                            value: department.name,
                            text: department.name
                        }));
                    });
                },
                error: function () {
                    console.error('Error fetching departments');
                }
            });
        });
        
        //Applicant Register
        $('#departmentSelectApplicant').change(function () {
            var departmentName = $(this).val();

            // Make an AJAX call to fetch departments based on the selected division
            $.ajax({
                url: '/api/teamName',
                type: 'GET',
                data: {departmentName: departmentName},
                success: function (data) {
                    // Clear existing options in the Department dropdown
                    $('#teamSelectApplicant').empty();

                    // Add a default option
                    $('#teamSelectApplicant').append($('<option>', {
                        value: '',
                        text: '----- Select Team -----'
                    }));

                    // Add new options based on the fetched departments
                    $.each(data, function (index, team) {
                        $('#teamSelectApplicant').append($('<option>', {
                            value: team.name,
                            text: team.name
                        }));
                    });
                },
                error: function () {
                    console.error('Error fetching departments');
                }
            });
        });
        
        $('#teamSelectApplicant').change(function () {
            var teamName = $(this).val();
			console.log("Team Name : " + teamName);
            // Make an AJAX call to fetch departments based on the selected division
            $.ajax({
                url: '/api/userName',
                type: 'GET',
                data: {teamName: teamName},
                success: function (data) {
                    // Clear existing options in the Department dropdown
                    $('#userSelectApplicant').empty();

                    // Add a default option
                    $('#userSelectApplicant').append($('<option>', {
                        value: '',
                        text: '----- Select User -----'
                    }));

                    // Add new options based on the fetched departments
                    $.each(data, function (index, user) {
                        $('#userSelectApplicant').append($('<option>', {
                            value: user.staffId,
                            text: user.name + ' (' + user.staffId + ')'
                        }));
                    });
                },
                error: function () {
                    console.error('Error fetching departments');
                }
            });
        });   
	    
    });
    