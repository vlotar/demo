<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Booking (Step 1)</title>
</head>
<body>
<h1>Step 1</h1>
<hr/>
<div>
    Confirmations: ${counter.confirmations}, Cancellations: ${counter.cancellations}
    <button onclick="confirmBooking();">Confirm</button>

    <div id="confirmationDialog" style="display:none;">
        Money will be charged from your Credit Card. Continue?
        <a href="${flowExecutionUrl}&_eventId_continue" onclick="">Yes</a>
        <a href="${flowExecutionUrl}&_eventId_cancel" onclick="">No</a>
    </div>

</div>
<script>
    function confirmBooking() {
        document.getElementById("confirmationDialog").style.display="block";
    }
</script>

</body>
</html>