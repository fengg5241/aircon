<% 
	String urlName = request.getRequestURI();
	String[] urlArray = urlName.split("/");
	String output = urlArray[urlArray.length-1];
	String file = output.substring(0, 1).toUpperCase() + output.substring(1, output.length()-4);
%>

<div class="row wrapper border-bottom white-bg page-heading">
	<div class="col-lg-8">
		<h2><%= file %></h2>
		<ol class="breadcrumb">
			<li>
				<a href="index.html">Group Selection</a>
			</li>
			<li>
				Germany
			</li>
			<li class="active">
				<strong>Fourth Level</strong>
			</li>
		</ol>
	</div>
</div>