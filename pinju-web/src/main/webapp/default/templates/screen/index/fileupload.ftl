	<#if retStr?exists>
	  <ul>
	  <#list retStr as ret>
	    <li>${ret}</li>
	    <img src="${imageServer}${ret}" />
	    <img src="${imageServer}${ret}" /><br>
	    <img src="${imageServer}${ret}_310x310" /><br>
	    <img src="${imageServer}${ret}_160x160" /><br>
	    <img src="${imageServer}${ret}_80x80" /><br>
	    <img src="${imageServer}${ret}_40x40" /><br>
	  </#list>
	  </ul>
	</#if>
	${errStr!""}
	
	<form action="http://10.245.130.228:8180/storage-ws/rest/img/" method="post" enctype="multipart/form-data">   
		上传文件：<input type="file" name="imgFile" /><br>   
		上传文件：<input type="file" name="imgFile" /><br>   
		上传文件：<input type="file" name="imgFile" /><br>   
		上传文件：<input type="file" name="imgFile" /><br>   
		上传文件：<input type="file" name="imgFile" /><br>   
		<input value="上传" type="submit" />
	</from>