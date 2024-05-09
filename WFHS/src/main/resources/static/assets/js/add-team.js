$(document).ready(function () {
	
	// Add Team
		$('#divisionSelectAddTeam').change(function () {
            var divisionId = $(this).val();

            // Make an AJAX request to fetch departments based on the selected division
            $.ajax({
                type: 'GET',
                url: '/api/getDepartmentsByDivision?divisionId=' + divisionId,
                success: function (data) {
                    // Update the department dropdown with the fetched data
                    $('#departmentSelectAddTeam').empty();
                    
                     $('#departmentSelectAddTeam').append($('<option>', {
                        value: '',
                        text: '----- Select Department -----'
                    }));
                    
                    $.each(data, function (index, department) {
                        $('#departmentSelectAddTeam').append('<option value="' + department.id + '">' + department.name + '</option>');
                    });
                },
                error: function () {
                    console.error('Error fetching departments');
                }
            });
        });   
        
        $("#departmentSelectAddTeam").change(function () {
            var selectedDepartmentId = $(this).val();
            $("#chooseDepartmentId").val(selectedDepartmentId);
            
            $.ajax({
                url: '/get-next-team-code/' + selectedDepartmentId,
                type: 'GET',
                success: function(nextTeamCode) {
                    // Set the next team code to the input field
                    $('#code').val(nextTeamCode);
                },
                error: function() {
                    console.error('Error fetching next team code');
                }
            });
        });
        
        
		$("#saveChangeBtn").click(function () {
			var divisionId = $('#divisionSelect').val();
            var departmentId = $("#chooseDepartmentId").val();
            var code = $("#code").val();
            var name = $("#name").val();

            var data = {
                code: code,
                name: name,
                departmentId: departmentId,
                divisionId: divisionId
            };

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/add-team",
                data: JSON.stringify(data),
                success: function (response) {
                    console.log(response);
                     window.location.reload(true);
                    // Optionally, you can reload the team list or update the modal UI
                    $("#exampleVerticallycenteredModal").modal('hide');
                },
                error: function (error) {
                    console.error("Error adding team", error);
                }
            });
        });
        
        
        //Serch by Name for Team List(Filter)
        function filterTeams() {
            var searchText = $('#teamSearchInput').val().toLowerCase();
            var departmentFilter = $('#departmentFilter').val();
            var divisionFilter = $('#divisionFilter').val();

            $('tbody tr').each(function () {
                var teamName = $(this).find('td:nth-child(3)').text().toLowerCase();
                var departmentName = $(this).find('td:nth-child(4)').text().toLowerCase();
                var divisionName = $(this).find('td:nth-child(5)').text().toLowerCase();

                var isMatch = teamName.includes(searchText);

                if (departmentFilter !== '') {
                    isMatch = isMatch && departmentName.includes(departmentFilter.toLowerCase());
                }

                if (divisionFilter !== '') {
                    isMatch = isMatch && divisionName.includes(divisionFilter.toLowerCase());
                }

                if (isMatch) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        }

        // Trigger the filter on keyup event in the search input and change event in dropdowns
        $('#teamSearchInput, #departmentFilter, #divisionFilter').on('keyup change', function () {
            filterTeams();
        });

         $('#teamSearchInput').on('input', function () {
            if ($(this).val() === '') {
                $('tbody tr').show();
            }
        });
        
         $.get("/getDepartments", function (data) {
            // Populate department dropdown options
            var departmentDropdown = $('#departmentFilter');
            departmentDropdown.empty();
            departmentDropdown.append($('<option>', {
                value: '',
                text: '----- Select Department -----'
            }));
            $.each(data, function (index, department) {
                departmentDropdown.append($('<option>', {
                    value: department.name,
                    text: department.name
                }));
            });
        });

        $.get("/getDivisions", function (data) {
            // Populate division dropdown options
            var divisionDropdown = $('#divisionFilter');
            divisionDropdown.empty();
            divisionDropdown.append($('<option>', {
                value: '',
                text: '----- Select Division -----'
            }));
            $.each(data, function (index, division) {
                divisionDropdown.append($('<option>', {
                    value: division.name,
                    text: division.name
                }));
            });
        });
        
        
        //Update Team name 
	    $('#updateTeamBtn').on('click', function() {
	        var modal = $('#editTeamModal');
	        var teamCode = modal.find('#teamCode').val();
	        console.log("Team code:", teamCode);
	        var teamNameInput = modal.find('#teamName').val();
	
	        // Create an object with updated data
	        var updatedData = {
	            code: teamCode,
	            name: teamNameInput
	            // Add more fields if needed
	        };
	
	        // Send an AJAX request to update the team data
	        $.ajax({
	            url: '/api/updateTeam',
	            type: 'POST',
	            contentType: 'application/json',
	            data: JSON.stringify(updatedData),
	            success: function(data) {
	                // Handle response from server if needed
	                console.log("Updated team data:", data);
	                window.location.reload(true);
	            },
	            error: function(xhr, textStatus, errorThrown) {
	                console.error('Error:', errorThrown);
	                // Handle error if needed
	            }
	        });
	    });
});

//Modal For Team Update
document.addEventListener("DOMContentLoaded", function() {
    const editButtons = document.querySelectorAll(".edit-btn");

    editButtons.forEach(function(btn) {
        btn.addEventListener("click", function() {
            const modal = document.getElementById("editTeamModal");
            const divisionNameInput = modal.querySelector("#divisionName");
            const departmentNameInput = modal.querySelector("#departmentName");
            const teamCodeInput = modal.querySelector("#teamCode");
            const teamNameInput = modal.querySelector("#teamName");

            // Extract data attributes from the clicked button
            const divisionName = btn.getAttribute("data-division-name");
            const departmentName = btn.getAttribute("data-department-name");
            const teamCode = btn.getAttribute("data-team-code");
            const teamName = btn.getAttribute("data-team-name");

            // Populate modal inputs with extracted data
            divisionNameInput.value = divisionName;
            departmentNameInput.value = departmentName;
            teamCodeInput.value = teamCode;
            teamNameInput.value = teamName;
        });
    });
});