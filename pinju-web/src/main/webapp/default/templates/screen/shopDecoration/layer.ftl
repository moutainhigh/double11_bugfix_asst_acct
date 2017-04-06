    <#if shopModulePrototypeList??>
    <div id="AddModuleDialog">
    <input type="hidden" name="type" id="moduleType" value="${type}">
    	<#list shopModulePrototypeList as shopModule>
    		<div class="module" module-name="${shopModule.name}" module-id="${shopModule.moduleId}" module-description="${shopModule.description}" is-custom-code="YES" module-title="${shopModule.title}">

            <div id="Mod-${shopModule.moduleId}" class="icon"></div>

            <div class="content">
                <a href="#add" class="add-module">添加</a>
                <h3>${shopModule.title}</h3>
                <h4>${shopModule.description}</h4>
            </div>
        </div>
        
    	</#list>
    	</div>
    </#if>
    
        <script type="text/javascript">
        	var shopModuleType = $('#moduleType').val();
            $(function() {
            	window.parent.dialog.caption('添加模块');
                $('.add-module').click(function() {
                    if (window.parent.layout_editor.addModule($(this).closest('.module'), shopModuleType)) {
                        window.parent.dialog.close();
                    };
                    return false;
                });
            });
        </script>
        
