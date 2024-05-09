$(document).ready(function () {
        
        //Filter
        $('#divisionFilter1').change(function () {
            var divisionName = $(this).val();

            // Make an AJAX call to fetch departments based on the selected division
            $.ajax({
                url: '/api/departmentName',
                type: 'GET',
                data: {divisionName: divisionName},
                success: function (data) {
                    // Clear existing options in the Department dropdown
                    $('#departmentFilter1').empty();

                    // Add a default option
                    $('#departmentFilter1').append($('<option>', {
                        value: '',
                        text: '----- Select Department -----'
                    }));

                    // Add new options based on the fetched departments
                    $.each(data, function (index, department) {
                        $('#departmentFilter1').append($('<option>', {
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
        $('#departmentFilter1').change(function () {
            var departmentName = $(this).val();

            // Make an AJAX call to fetch departments based on the selected division
            $.ajax({
                url: '/api/teamName',
                type: 'GET',
                data: {departmentName: departmentName},
                success: function (data) {
                    // Clear existing options in the Department dropdown
                    $('#teamFilter1').empty();

                    // Add a default option
                    $('#teamFilter1').append($('<option>', {
                        value: '',
                        text: '----- Select Team -----'
                    }));

                    // Add new options based on the fetched departments
                    $.each(data, function (index, team) {
                        $('#teamFilter1').append($('<option>', {
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
        
        
        //Serch by Name for Team List(Filter)
        function filterTeams() {
            
            var divisionFilter = $('#divisionFilter1').val();
            var departmentFilter = $('#departmentFilter1').val();
            var searchText = $('#teamFilter1').val();
            

            $('tbody tr').each(function () {
                var teamName = $(this).find('td:nth-child(5)').text().toLowerCase();
                var departmentName = $(this).find('td:nth-child(6)').text().toLowerCase();
                var divisionName = $(this).find('td:nth-child(7)').text().toLowerCase();

                var isMatch = teamName.includes(searchText.toLowerCase());

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
        $('#teamFilter1, #departmentFilter1, #divisionFilter1').on('keyup change', function () {
            filterTeams();
        });

	    
    });

		
    