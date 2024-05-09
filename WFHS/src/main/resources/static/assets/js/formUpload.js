		function previewImage(event, imageId) {
			const input = event.target;
			const image = document.getElementById(imageId);
			const reader = new FileReader();
			reader.onload = function () {
				if (input.files && input.files[0]) {
		            var file = input.files[0];
		            var maxSize = 10 * 1024 * 1024; // 10MB
		            if (file.size > maxSize) {
		                alert('Image size exceeds the limit (10MB)');
		                // Clear the file input to prevent submission of oversized image
		                input.value = '';
		                return;
		            }
		            
					image.src = reader.result;
					image.style.border = '3px solid green';
			
				}
			};
			if (input.files && input.files[0]) {
				reader.readAsDataURL(input.files[0]);
			}
		}
		
function requiredImg() {
	var radioButtons = document.getElementsByName('osType');
	var pledgeCheckbox = document.getElementById('pledgeCheckbox');
	var signatureImgInput = document.getElementById('signatureImg');
	var submit = false;
	var selectedOs;
	
	

	for (var i = 0; i < radioButtons.length; i++) {
		if (radioButtons[i].checked) {
			selectedOs = radioButtons[i].value;
			break;
		}
	}
	if (selectedOs == 'window') {
		var window1 = $('#uploadButton1').val() || '';
		var window2 = $('#uploadButton2').val() || '';
		var window3 = $('#uploadButton3').val() || '';
		var window4 = $('#uploadButton4').val() || '';
		var window5 = $('#uploadButton5').val() || '';
		if (window1 == '' || window2 == '' || window3 == '' || window4 == '' || window5 == '') {
			alert("Upload uncompleted photos for PC Requirement(" + selectedOs + ")");
			$('#windowModal').modal('show');
			return false; // Prevent form submission
		} else {
			submit = true;
		}
	}
	if (selectedOs == 'mac') {
		var mac1 = $('#uploadButton6').val() || '';
		var mac2 = $('#uploadButton7').val() || '';
		var mac3 = $('#uploadButton8').val() || '';
		if (mac1 == '' || mac2 == '' || mac3 == '') {
			alert("Upload uncompleted photos for PC Requirement(" + selectedOs + ")");
			$('#macModal').modal('show');
			return false; // Prevent form submission
		} else {
			submit = true;
		}
	}
	if (selectedOs == 'linux') {
		var linux1 = $('#uploadButton9').val() || '';
		var linux2 = $('#uploadButton10').val() || '';
		var linux3 = $('#uploadButton11').val() || '';
		if (linux1 == '' || linux2 == '' || linux3 == '') {
			alert("Upload uncompleted photos for PC Requirement(" + selectedOs + ")");
			$('#linuxModal').modal('show');
			return false; // Prevent form submission
		} else {
			submit = true;
		}
	}
	for (var i = 0; i < uploadButtons.length; i++) {
		var photoName = document.getElementById(uploadButtons[i]).files[0]?.name || '';
		if (photoName === '') {

			if (selectedOs == 'mac') { $('#macModal').modal('show'); }
			if (selectedOs == 'linux') { $('#linuxModal').modal('show'); }
			return false;
		}
	}
}
		var userself = $("#userself").val();
		if(userself == 0){
		 $("#name").val("");
		 $("#currentPosition").val("");
		 $("#teamName").val("");
		 $("#departmentName").val("");
			
		}
		
$(document).ready(function() {
	$('#staffId').change(function() {
		var selectedStaffId = $(this).val();

		$.ajax({
			url: '/api/v1/users/' + selectedStaffId,
			method: 'GET',
			success: function(data) {
				$("#name").val(data.name);
				$("#currentPosition").val(data.currentPosition);
				$("#teamName").val(data.teamName);
				$("#departmentName").val(data.departmentName);
			},
			error: function(xhr, status, error) {
				console.error('Error:', error);
			}
		});
	});
});




 $(document).ready(function() {
	 
	 $("#windowDivision").hide();
	 $("#macDivision").hide();
	 $("#linuxDivision").hide();
	 
        $("#window").on("click", function() {
            if ($(this).is(":checked")) {
                $("#windowDivision").show();
                $("#macDivision").hide();
				$("#linuxDivision").hide();
            } else {
               
            }
        });
        $("#mac").on("click", function() {
            if ($(this).is(":checked")) {
                $("#macDivision").show();
                $("#windowDivision").hide();
				$("#linuxDivision").hide();
            } else {
               
            }
        });
        
        $("#linux").on("click", function() {
            if ($(this).is(":checked")) {
                $("#linuxDivision").show();
                $("#windowDivision").hide();
				$("#macDivision").hide();
            } else {
               
            }
        });
    });