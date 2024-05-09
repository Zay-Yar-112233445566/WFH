$(document).ready(function () {
	
	 //Add Department
        $("#input43").change(function () {
            var selectedDivisionId = $(this).val();
            $("#chooseDivisionId").val(selectedDivisionId);
            
            $.ajax({
                url: '/get-next-department-code/' + selectedDivisionId,
                type: 'GET',
                success: function(nextDepartmentCode) {
                    // Set the next team code to the input field
                    $('#codeDepartment').val(nextDepartmentCode);
                },
                error: function() {
                    console.error('Error fetching next team code');
                }
            });
        });
        
        $("#saveChangeBtnDepartment").click(function () {
            var divisionId = $("#chooseDivisionId").val();
            var code = $("#codeDepartment").val();
            var name = $("#nameDepartment").val();

            var data = {
                code: code,
                name: name,
                divisionId: divisionId
            };

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/add-department",
                data: JSON.stringify(data),
                success: function (response) {
                    console.log(response);
                    window.location.reload(true);
                    // Optionally, you can reload the team list or update the modal UI
                    $("#exampleVerticallycenteredModal1").modal('hide');
                },
                error: function (error) {
                    console.error("Error adding department", error);
                }
            });
        });
        
        
         //Serch by Name for Department List  
        function filterDepartments() {
            var departmentFilter = $('#departmentFilter1').val().toLowerCase();

            $('tbody tr').each(function () {
                var departmentName = $(this).find('td:nth-child(3)').text().toLowerCase();

                var isMatch = (departmentFilter === '' || departmentName.includes(departmentFilter));

                if (isMatch) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        }

        function filterDivisions() {
            var divisionFilter = $('#divisionFilter1').val().toLowerCase();

            $('tbody tr').each(function () {
                var divisionName = $(this).find('td:nth-child(4)').text().toLowerCase();

                var isMatch = (divisionFilter === '' || divisionName.includes(divisionFilter));

                if (isMatch) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        }
        
        $('#departmentFilter1').on('change', function () {
            filterDepartments();
        });

        $('#divisionFilter1').on('change', function () {
            filterDivisions();
        });
        
        $.get("/getDepartments", function (data) {
            // Populate department dropdown options
            var departmentDropdown = $('#departmentFilter1');
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
            var divisionDropdown = $('#divisionFilter1');
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
        
        
        //Update Department name 
	    $('#updateDepartmentBtn').on('click', function() {
	        var modal = $('#editDepartmentModal');
	        var departmentCode = modal.find('#departmentCode').val();
	        console.log("Department code:", departmentCode);
	        var departmentNameInput = modal.find('#departmentName').val();
	
	        // Create an object with updated data
	        var updatedData = {
	            code: departmentCode,
	            name: departmentNameInput
	            // Add more fields if needed
	        };
	
	        // Send an AJAX request to update the team data
	        $.ajax({
	            url: '/api/updateDepartment',
	            type: 'POST',
	            contentType: 'application/json',
	            data: JSON.stringify(updatedData),
	            success: function(data) {
	                // Handle response from server if needed
	                console.log("Updated department data:", data);
	                window.location.reload(true);
	            },
	            error: function(xhr, textStatus, errorThrown) {
	                console.error('Error:', errorThrown);
	                // Handle error if needed
	            }
	        });
	    });
	    
});


//Modal for Department update
document.addEventListener("DOMContentLoaded", function() {
    const editButtons = document.querySelectorAll(".edit-department-btn");

    editButtons.forEach(function(btn) {
        btn.addEventListener("click", function() {
            const modal = document.getElementById("editDepartmentModal");
            const divisionNameInput = modal.querySelector("#divisionName");
            const departmentCodeInput = modal.querySelector("#departmentCode");
            const departmentNameInput = modal.querySelector("#departmentName");

            // Extract data attributes from the clicked button
            const divisionName = btn.getAttribute("data-division-name");
            const departmentCode = btn.getAttribute("data-department-code");
            const departmentName = btn.getAttribute("data-department-name");

            // Populate modal inputs with extracted data
            divisionNameInput.value = divisionName;
            departmentCodeInput.value = departmentCode;
            departmentNameInput.value = departmentName;
        });
    });
});