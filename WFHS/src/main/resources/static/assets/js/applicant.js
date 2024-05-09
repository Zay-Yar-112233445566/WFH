$(document).ready(function () {
	
	//for Applicant Id
        $('#staffId').prop('readonly', true);
        $('#genderMale, #genderFemale').change(function()  {
            var gender = $(this).val();
            var staffIdInput = $('#staffId'); 

			if (gender === 'M') {
                 staffIdInput.val('25-');
            } else if (gender === 'F') {
                 staffIdInput.val('26-');
            }

            staffIdInput.prop('readonly', false);
        });
        
        
        $('#staffId').on('input', function() {
	        var staffId = $(this).val().trim(); 

	        // Check if the input is empty
	        if (staffId === '') {
	            hideErrorMessage();
	            return;
	        }
	        checkStaffIdExist(staffId);
	    });
        
        
        //Auto fill user info
        $('#staffIdAutoFill').on('change', function() {
			console.log("I am here");
            var selectedStaffId = $(this).val();
	        if(selectedStaffId !== ''){
				console.log("selected staff id : " + selectedStaffId);
	            // Ajax call to fetch data for the selected staffId
	            $.ajax({
	                url: '/api/fetchUserData', // Replace with your actual URL to fetch user data
	                type: 'GET',
	                data: { staffId: selectedStaffId },
	                success: function(response){
	                    // Assuming response contains data in JSON format
	                    $('#staffName').val(response.name);
	                    $('#division').val(response.divisionName);
	                    $('#department').val(response.departmentName);
	                    $('#team').val(response.teamName);
	                    $('#position').val(response.currentPosition);
	                },
	                error: function(xhr, status, error){
	                    // Handle error
	                    console.error(error);
	                }
	            });
	        }
		});
		
		
		
		//Validate 
		$('#input42').on('input',function() {
            var name = $(this).val();

            // Name validation
            if (!isValidName(name)) {
                $('#nameErrorMessage').text('Name should be in the format "John Doe"').show();
            } else {
                $('#nameErrorMessage').hide(); // Hide error message if name is valid
            }
        });
        
        
        $('#input43').on('input',function() {
            var phone = $(this).val();

            // Phone number validation
            if (!isValidPhoneNumber(phone)) {
                $('#phoneErrorMessage').text('Phone No should start with "09-" followed by minimum of 7 and maximun of 9 digits').show();
            } else {
                $('#phoneErrorMessage').hide(); // Hide error message if phone number is valid
            }
        });
        
        
        $('#joinDate, #permanentDate').change(function() {
	        var joinDate = $('#joinDate').val();
	        var permanentDate = $('#permanentDate').val();
	
	        // Validate if Permanent Date is earlier than Join Date
	        if (joinDate && permanentDate && permanentDate < joinDate) {
	            showPermanentDateErrorMessage('Permanent Date cannot be earlier than Join Date');
	        } else {
	            hidePermanentDateErrorMessage();
	        }
	    });
	    
	    
	    $('#email').on('input', function() {
            var email = $(this).val();
            if (email.trim() !== '') {
                $.ajax({
                    url: '/api/check-email-exists',
                    type: 'GET',
                    data: { email: email },
                    dataType: 'json',
                    success: function(data) {
                        if (data.exists) {
                            $('#emailErrorMessage').show();
                            $('#submitButton').prop('disabled', true);
                        } else {
                            $('#emailErrorMessage').hide();
                            $('#submitButton').prop('disabled', false);
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', error);
                    }
                });
            } else {
                $('#emailErrorMessage').hide();
                $('#submitButton').prop('disabled', false);
            }
        });
	    
});

function checkStaffIdExist(staffId) {
		
		if (staffId.length !== 8) {
	        showErrorMessage('StaffID should be 8 characters long');
	        return;
	    }

	    $.ajax({
	        url: '/api/checkStaffId',
	        method: 'POST',
	        contentType: 'application/json',
	        data: JSON.stringify({ staffId: staffId }),
	        success: function(response) {
	            if (response.exists) {
	                showErrorMessage('Staff ID already exists');
	            } else {
	                hideErrorMessage();
	            }
	        },
	        error: function(xhr, status, error) {
	            console.error('Error checking staff ID existence:', error);
	        }
	    });
	}
	
	
	//Staff Id
	function showErrorMessage(message) {
	    $('#staffId').addClass('border border-danger');
	    $('#staffIdErrorMessage').text(message).show();
	}
		
	function hideErrorMessage() {
	    $('#staffId').removeClass('border border-danger');
	    $('#staffIdErrorMessage').hide();
	}

	
	//Permanent Date 
	function showPermanentDateErrorMessage(message) {
	    $('#permanentDate').addClass('border border-danger');
	    $('#permanentDateErrorMessage').text(message).show();
	}
	
	function hidePermanentDateErrorMessage() {
	    $('#permanentDate').removeClass('border border-danger');
	    $('#permanentDateErrorMessage').hide();
	}
	
	
	//Name
	function isValidName(name) {
         var nameRegex = /^[A-Z][a-z]+(\s[A-Z][a-z]+)*$/;
         return nameRegex.test(name.trim());
    }

    // Function to validate phone number format
    function isValidPhoneNumber(phone) {
         var phoneRegex = /^09-\d{7,9}$/;
         return phoneRegex.test(phone.trim());
    }
