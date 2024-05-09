$(document).ready(function () {
	
	//Add Division
        $('#exampleVerticallycenteredModal2').on('show.bs.modal', function () {
            // Make an AJAX request to the server to get a new division code
            $.ajax({
                type: 'GET',
                url: '/getNewDivisionCode',
                success: function (data) {
                    // Update the value of the division code input field
                    $('#codeDivision').val(data);
                },
                error: function () {
                    console.error('Error while getting new division code');
                }
            });
        });

        
        $("#saveChangeBtnDivision").click(function () {
            var code = $("#codeDivision").val();
            var name = $("#nameDivision").val();

            var data = {
                code: code,
                name: name,
            };

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/add-division",
                data: JSON.stringify(data),
                success: function (response) {
                    console.log(response);
                    window.location.reload(true);
                    // Optionally, you can reload the team list or update the modal UI
                    $("#exampleVerticallycenteredModal2").modal('hide');
                },
                error: function (error) {
                    console.error("Error adding division", error);
                }
            });
        });
        
        //Update Division name 
	    $('#updateDivisionBtn').on('click', function() {
	        var modal = $('#editDivisionModal');
	        var divisionCode = modal.find('#divisionCode').val();
	        console.log("Division code:", divisionCode);
	        var divisionNameInput = modal.find('#divisionName').val();
	
	        // Create an object with updated data
	        var updatedData = {
	            code: divisionCode,
	            name: divisionNameInput
	            // Add more fields if needed
	        };
	
	        // Send an AJAX request to update the team data
	        $.ajax({
	            url: '/api/updateDivision',
	            type: 'POST',
	            contentType: 'application/json',
	            data: JSON.stringify(updatedData),
	            success: function(data) {
	                // Handle response from server if needed
	                console.log("Updated division data:", data);
	                window.location.reload(true);
	            },
	            error: function(xhr, textStatus, errorThrown) {
	                console.error('Error:', errorThrown);
	                // Handle error if needed
	            }
	        });
	    });
	
});


//Modal for Division update
document.addEventListener("DOMContentLoaded", function() {
    const editButtons = document.querySelectorAll(".edit-division-btn");

    editButtons.forEach(function(btn) {
        btn.addEventListener("click", function() {
            const modal = document.getElementById("editDivisionModal");
            const divisionCodeInput = modal.querySelector("#divisionCode");
            const divisionNameInput = modal.querySelector("#divisionName");

            // Extract data attributes from the clicked button
            const divisionCode = btn.getAttribute("data-division-code");
            const divisionName = btn.getAttribute("data-division-name");

            // Populate modal inputs with extracted data
            divisionCodeInput.value = divisionCode;
            divisionNameInput.value = divisionName;
        });
    });
});
