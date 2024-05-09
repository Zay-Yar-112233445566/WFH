function toggleCheckboxes(checkbox) {
	var checkboxes = document.getElementsByClassName("rowCheckbox");
	for (var i = 0; i < checkboxes.length; i++) {
		checkboxes[i].checked = checkbox.checked;
	}
}

function batchApprove(action) {
	var selectedIds = [];
	var checkboxes = document.getElementsByClassName("rowCheckbox");
	var reasonId = action === 'approve' ? 'approveReason' : 'rejectReason' ;
	var approveReason = document.getElementById(reasonId).value;	
	var status = action === 'approve' ? 1 : 2;
	for (var i = 0; i < checkboxes.length; i++) {
		if (checkboxes[i].checked) {
			selectedIds.push(parseInt(checkboxes[i].value));
		}
	}
	if (selectedIds.length > 0) {
	
		var url = '/approved/forms?idList=' + selectedIds.join(',') + '&approveReason=' + encodeURIComponent(approveReason) +'&status=' + status;
		$.ajax({
			url: url,
			method: 'POST',
			contentType: 'application/json',
			success: function() {
				window.location.href = '/approved/forms';
			},
			error: function(xhr, status, error) {
				console.error('Error:', error);
			}
		});

	} else {
		alert("No checkboxes selected.");
	}
}