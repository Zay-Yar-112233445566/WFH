$(document).ready(function(){
    // Function to validate the form
    function validateForm() {
		
        // Check if all file inputs are filled
        var filesFilled = $('input[type="file"]').filter(function() {
            return $.trim($(this).val()) != '';
        }).length;
        // Check if all checkboxes are checked
        var checkboxesChecked = $('[type="checkbox"]:checked').length;
	    
        if (filesFilled < 4 || checkboxesChecked < 6) {
            alert('Please fill all required data');
            return false; // Prevent form submission
        }
        return true; // Allow form submission
    }

    // Bind click event to Submit button
    $('.btn-primary').click(function(e) {
        e.preventDefault(); // Prevent default form submission
        // Validate form
        if (validateForm()) {
            // If form is valid, prepare form data for AJAX submission
            var formData = new FormData($('form')[0]); // Capture all form data including files

            // Submit form using AJAX
            $.ajax({
                type: 'POST',
                url: '/applied/forms',
                data: formData,
                processData: false, // Prevent jQuery from automatically processing the data
                contentType: false, // Prevent jQuery from automatically setting the content type
                success: function(response) {
                    // Handle success response
                    console.log(response);
                    alert('Form submitted successfully');
                    window.location.href = '/applied/forms/'+0;
                },
                error: function(xhr, status, error) {
                    // Handle error response
                    console.error(xhr.responseText);
                    alert('Error occurred while submitting the form');
                }
            });
        }
    });
});