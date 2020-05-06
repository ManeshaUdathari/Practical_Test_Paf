<%@page import="com.hcs.appointment.Appointment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Appointment Management</title>
<link rel="stylesheet" href="views/bootstrap.min.css">
<script src="components/jquery-3.2.1.min.js"></script>
<script src="components/Appointments.js"></script>
</head>
<body>
<div class="container">
<div class="row">
<div class="col-6">
		<h1>Appointment Management </h1>
		
		<form id="formAppointment" name="formAppointment">
 				Patient Name:
 				<input id="PatientName" name="PatientName" type="text"
 								class="form-control form-control-sm">
 				<br> Patient Email:
 				<input id="PatientEmail" name="PatientEmail" type="text"
 								class="form-control form-control-sm">
 				<br> Doctor Name:
				<input id="DoctorName" name="DoctorName" type="text"
 								class="form-control form-control-sm">
 				<br> Reason:
 				<input id="Reason" name="Reason" type="text"
 								class="form-control form-control-sm">
 				<br>
 				<input id="btnSave" name="btnSave" type="button" value="Save"
 								class="btn btn-primary">
 				<input type="hidden" id="hidAppointmentIDSave"
 								name="hidAppointmentIDSave" value="">
		</form>
		
		<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>
		<br>
		<div id="divAppointmentGrid">
				 <%
 						Appointment appointmentObj = new Appointment();
 						out.print(appointmentObj.readAppointment());
 				%>
		</div>

				</div>
 			</div>
		</div>

</body>
</html>