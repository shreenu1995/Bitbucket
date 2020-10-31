$(document).ready(
		function() {
			$('#header').load('header.html');
			$('#footer').load('footer.html');
			var date = new Date();
			var today = new Date(date.getFullYear(), date.getMonth(), date
					.getDate());
			var start = new Date();

			$('#generationDateStart').datepicker({
				autoclose : true,
				todayHighlight : true,
				orientation : "bottom",
				endDate : today,
				format : 'yyyy-mm-dd'
			}).datepicker('update', new Date()).on(
					'changeDate',
					function() {
						$('#generationDateEnd').datepicker('setStartDate',
								new Date($(this).val()));
					});

			$('#generationDateEnd').datepicker({
				autoclose : true,
				todayHighlight : true,
				startDate : start,
				orientation : "bottom",
				endDate : today,
				format : 'yyyy-mm-dd'
			}).datepicker('update', new Date()).on(
					'changeDate',
					function() {
						$('#generationDateStart').datepicker('setEndDate',
								new Date($(this).val()));
					});

			$("#generationDateStartDate span.input-group-addon").click(
					function() {
						$("#generationDateStart").datepicker('show');
					});

			$("#generationDateEndDate span.input-group-addon").click(
					function() {
						$('#generationDateEnd').datepicker('show');
					});
         });

function resetTerminalSetupReport() {
	setDiv('equimentOemIdDiv', '&nbsp');
	setDiv('equimentModelNumberDiv', '&nbsp');
	setDiv('pageIndexDiv', '&nbsp');
}
