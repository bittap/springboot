<%
	StringBuilder path = new StringBuilder(request.getContextPath());
	path.append("/");
%>
<link rel="stylesheet" type="text/css" href="<%=path %>themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path %>demo/demo.css">
<script type="text/javascript" src="<%=path %>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>js/jquery.easyui.min.js"></script>