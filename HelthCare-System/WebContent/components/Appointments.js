$(document).ready(function()
{
		if ($("#alertSuccess").text().trim() == "")
		{
			$("#alertSuccess").hide();
		}
		$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
		// Clear alerts---------------------
		$("#alertSuccess").text("");
		$("#alertSuccess").hide();
		$("#alertError").text("");
		$("#alertError").hide();
		
		// Form validation-------------------
		var status = validateAppointmentForm();
		if (status != true)
		{
			$("#alertError").text(status);
			$("#alertError").show();
			return;
		}
		// If valid------------------------
		var type = ($("#hidAppointmentIDSave").val() == "") ? "POST" : "PUT"; 
		
		$.ajax(
				{  url : "AppointmentAPI", 
				   type : type,  
				   data : $("#formAppointment").serialize(),  
				   dataType : "text",  
				   complete : function(response, status)  
				   {   
					   onAppointmentSaveComplete(response.responseText, status); 
				    } 
			  });
	}); 



function onAppointmentSaveComplete(response, status)
{
	if (status == "success") {
		alert(response)
		var resultSet = JSON.parse(response); 
		
		if (resultSet.status.trim() == "success") {  
			$("#alertSuccess").text("Successfully saved."); 
			$("#alertSuccess").show(); 

		    $("#divAppointmentGrid").html(resultSet.data); 
		    } else if (resultSet.status.trim() == "error") { 
		    	
		    	$("#alertError").text(resultSet.data);  
		    	$("#alertError").show(); 
		    	
		    }else if (status == "error") {  
		    	$("#alertError").text("Error while saving.");  
		    	$("#alertError").show(); 
		    	
		    } else {  $("#alertError").text("Unknown error while saving..");  
		              $("#alertError").show(); 
		           }
		
		$("#hidAppointmentIDSave").val(""); 
		$("#formAppointment")[0].reset(); 
		
	}
	
}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
		$("#hidAppointmentIDSave").val($(this).closest("tr").find('#hidAppointmentIDUpdate').val());
		$("#PatientName").val($(this).closest("tr").find('td:eq(0)').text());
		$("#PatientEmail").val($(this).closest("tr").find('td:eq(1)').text());
		$("#DoctorName").val($(this).closest("tr").find('td:eq(2)').text());
		$("#Reason").val($(this).closest("tr").find('td:eq(3)').text());
});

// CLIENTMODEL=========================================================================
function validateAppointmentForm()
{
		// Patient Name----------------
		if ($("#PatientName").val().trim() == "")
		{
			return "Insert Patient Name.";
		}
		// Patient Email------------------------
		if ($("#PatientEmail").val().trim() == "")
		{
			return "Insert Patient Email.";
		} 

		var emailReg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		var tmpEmail = $("#PatientEmail").val().trim();
		if(!tmpEmail.match(emailReg)){
			return "Insert a valid email......!";
		}
		
		// Doctor Name-------------------------------
		if ($("#DoctorName").val().trim() == "")
		{
			return "Insert Doctor Name.";
		}
		
		// Reason-------------------------------
		if ($("#Reason").val().trim() == "")
		{
			return "Insert Reason.";
		}
		
		return true;
}


//Remove
$(document).on("click", ".btnRemove", function(event)
{
		 $.ajax(
		 {
			 url : "AppointmentAPI",
			 type : "DELETE",
			 data : "AppointmentID=" + $(this).data("appointmentid"),
			 dataType : "text",
			 complete : function(response, status)
			 {
				 
				 onAppointmentDeleteComplete(response.responseText, status);
			 }	
		 });
});

function onAppointmentDeleteComplete(response, status)
{
	if (status == "success")
	{
		
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divAppointmentGrid").html(resultSet.data);
			
		} else if (resultSet.status.trim() == "error")
		{
			
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		
	} else if (status == "error")
	{
		
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else
	{
			
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}
