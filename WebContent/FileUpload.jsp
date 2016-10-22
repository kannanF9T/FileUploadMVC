<html>
<head>
<title>File Uploading Form</title>
</head>
<body>
<h3>File Upload:</h3>
Select a file to upload: <br />
<form action="FileServlet" method="post"
                        >
<input type="file" name="file1" id='filer' size="50" onpropertychange='callFileName();'/>
<input type="hidden" id='one' name='one' size='50' value='two'/>
<br />
<input type="submit" value="Upload File" />
</form>
<script>
function callFileName(){
	alert("File Name::"+document.getElementById("filer").value);
}

</script>
</body>
</html>