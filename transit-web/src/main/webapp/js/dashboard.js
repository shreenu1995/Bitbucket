 var data = [
        	['organizations', 20],
        	['ptos', 10],
        	['vehicles', 30],
        	['operators', 30],
        	['devices', 10]
        ];
 
	 var data1 = [
		 ['Tickets', 40],
		 ['Passes', 60],
	 ];
	 
	 var data2 = [
		 ['Tickets', 80],
		 ['Passes', 20],
	 ];
	 
 var data3 = [
	 ['Tickets', 70],
	 ['Passes', 30],
 ];
 
 var dataDaily = [
	    ['Tickets', 'Passes'],
	    [150, 353]
	];

	var dataWeekly = [
	    ['Tickets', 'Passes'],
	    [75, 369]
	];

	var dataMonthly = [
	    ['Tickets', 'Passes'],
	    [4598, 2869]  
	];

        
        var chart  = c3.generate({
        	bindto: '#overall-count',
        	data: {
        		columns : data,
        		type : 'pie'
        	},
        	
        	size: {
        		width : 420,
        		height : 220
        	}
        });
        
		 var chart  = c3.generate({
		 	bindto: '#ticket-pass',
		 	data: {
		 		columns : data1,
		 		type : 'pie'
		 	},
		 	
		 	size: {
		 		width : 420,
        		height : 220
		 	}
		 });
		 
		 var chart  = c3.generate({
			 	bindto: '#revenue-transaction',
			 	data: {
			 		rows: dataDaily,
			 		type : 'pie'
			 	},
			 	
			 	size: {
			 		width : 420,
	        		height : 220
			 	},
			 	
			 });
		 
		 var chart  = c3.generate({
			 	bindto: '#fee-commisssion',
			 	data: {
			 		rows: dataDaily,
			 		type : 'pie'
			 	},
			 	
			 	size: {
			 		width : 420,
	        		height : 220
			 	}
			 });
		 
		 
		    $("#revenue-report").change(function (evt) {
		        var timeSelection = eval($("#revenue-report").val());
		        c3.generate({
		        	bindto: '#revenue-transaction',
		            data: {
		                rows: timeSelection,
		                type: 'pie' 
		            },
		            
		            size: {
				 		width : 420,
		        		height : 220
				 	}
		        });
		    });
		    
		    $("#fee-report").change(function (evt) {
		        var timeSelection = eval($("#fee-report").val());
		        c3.generate({
		        	bindto: '#fee-commisssion',
		            data: {
		                rows: timeSelection,
		                type: 'pie' 
		            },
		            
		            size: {
				 		width : 420,
		        		height : 220
				 	}
		        });
		    });