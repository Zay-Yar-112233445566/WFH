function approve(role, action) {
	var reasonId =
		role === 'PROJECT_MANAGER' ? 'managerApproveReason' :
			role === 'DEPARTMENT_HEAD' ? 'departmentHeadApproveReason' :
				role === 'DIVISION_HEAD' ? 'divisionHeadApproveReason' :
					role === 'CEO' ? 'CEOApproveReason' : 
					role === 'CISO' ? 'CISOApproveReason': 'serviceDeskSuggestion';
	var url = '/approved/form/';
	var request = {
		id: $("#operationId").val(),
		status: action === 'approve' ? 1 :  2,
		comment: document.getElementById(reasonId).value ,
	};
	
	if (role === 'CISO' && action === 'approve') {
        // Check if all checkboxes are marked
        var allChecked = document.querySelectorAll('.form-check-input:checked').length === document.querySelectorAll('.form-check-input').length;

        if (!allChecked) {
			
			alert('Please fill all required data');
            return; // Exit the function if not all checkboxes are marked
        }
    }
    
		$.ajax({
			url: url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(request),
			success: function() {
				if (action === 'approve') {
	                console.log("Approve Process");
	                alert('Your Process successfully!');
	                /*$('#successMessage').show();*/
	                setTimeout(function () {
	                    window.location.href = '/approved/forms';
	                }, 1000);
               }else {
					console.log("I am here");
					var staffId = $("#input35").val();
	                $.ajax({
		                url: '/send_rejection_email', // Your new endpoint that sends the email
		                method: 'POST',
		                data: {
		                    staffId: staffId, // Assuming staffId is available
		                    reason: document.getElementById(reasonId).value,
		                },
		                success: function () {
							console.log("I am here");
							alert('The form has been rejected');
		                    window.location.href = '/approved/forms';
		                },
		                error: function (xhr, status, error) {
		                    console.error('Error:', error);
		                }
		            });
	            }
			},
			error: function(xhr, status, error) {
				console.error('Error:', error);
			}
		});
}

$(document).ready(function() {
    $('#listConfirm').change(function() {
        if ($(this).prop('checked')) {
            $('#listConfirmLabel').removeClass('text-dark').addClass(' text-success');
            $('.ciso-check').removeClass('text-danger').addClass('text-success');
        } else {
            $('#listConfirmLabel').removeClass('text-success').addClass(' text-dark');
            $('.ciso-check').removeClass('text-success').addClass('text-danger');
        }
    });
});

$(document).ready(function() {
    $('#confirmAntivirusFullScan').change(function() {
        if ($(this).prop('checked')) {
            $('#checkList6').removeClass('text-danger').addClass(' text-success');
            $('.check6').removeClass('text-danger').addClass('text-success');
        } else {
            $('#checkList6').removeClass('text-success').addClass(' text-dark');
            $('.check6').removeClass('text-success').addClass('text-danger');
        }
    });
});

$(document).ready(function() {
    $('#confirmAntivirusPattern').change(function() {
        if ($(this).prop('checked')) {
            $('#checkList5').removeClass('text-danger').addClass(' text-success');
            $('.check5').removeClass('text-danger').addClass('text-success');
        } else {
            $('#checkList5').removeClass('text-success').addClass(' text-dark');
            $('.check5').removeClass('text-success').addClass('text-danger');
        }
    });
});

$(document).ready(function() {
    $('#confirmAntivirusSoftware').change(function() {
        if ($(this).prop('checked')) {
            $('#checkList4').removeClass('text-danger').addClass(' text-success');
            $('.check4').removeClass('text-danger').addClass('text-success');
        } else {
            $('#checkList4').removeClass('text-success').addClass(' text-dark');
            $('.check4').removeClass('text-success').addClass('text-danger');
        }
    });
});

$(document).ready(function() {
    $('#confirmSecurityPatch').change(function() {
        if ($(this).prop('checked')) {
            $('#checkList3').removeClass('text-danger').addClass(' text-success');
            $('.check3').removeClass('text-danger').addClass('text-success');
        } else {
            $('#checkList3').removeClass('text-success').addClass(' text-dark');
            $('.check3').removeClass('text-success').addClass('text-danger');
        }
    });
});

$(document).ready(function() {
    $('#confirmBrowser').change(function() {
        if ($(this).prop('checked')) {
            $('#checkList2').removeClass('text-danger').addClass(' text-success');
            $('.check2').removeClass('text-danger').addClass('text-success');
        } else {
            $('#checkList2').removeClass('text-success').addClass(' text-dark');
            $('.check2').removeClass('text-success').addClass('text-danger');
        }
    });
});

$(document).ready(function() {
    $('#confirmOs').change(function() {
        if ($(this).prop('checked')) {
            $('#checkList1').removeClass('text-danger').addClass(' text-success');
            $('.check1').removeClass('text-danger').addClass('text-success');
        } else {
            $('#checkList1').removeClass('text-success').addClass(' text-dark');
            $('.check1').removeClass('text-success').addClass('text-danger');
        }
    });
});
