$(document).ready(

function() {

	initItem();
	
	$("#updateCategory").click(function() {
		$("#itemReleasedForm").attr( {
			action : "categoryList.htm?",
			method : "post",
			enctype : "multipart/form-data"
		}).submit();
	});

	$("#released").click(function() {
		if (validateItemInput(true)) {
			$("#itemReleasedForm").attr( {
				action : "itemUpdate.htm",
				method : "post",
				enctype : "multipart/form-data"
			}).submit();
		}
	});

	/*
	for (var i = 1; i <= 5; i++) {
		$.imagePreview({
			file : $('#picFile' + i),
			img : $('#picImg' + i),
			maxWidth : 100,
			maxHeight : 100
		});
	}
	*/

	//初始化类目属性关系
		$("#itemAtt").find("select").each(function() {
			$(this).change(function() {
				getSonPro($(this));
			});
		});

		//初始化SPU
		$(".spu-select").each(function() {
			var cid = $(this).attr("cId");
			var vid = $(this).val();
			var spu = new Spu(cid, vid);
			$(this).change(function() {
				var cid = $(this).attr("cId");
				var vid = $(this).val();
				var spu = new Spu(cid, vid);
			});
		});

		//运费模板
		showListTemplate(1);

		var sku = new Sku();
		$("#skuList").find("input").each(function() {
			var pv = $(this).attr("pv");
			var stock = $(this).attr("stock");
			var price = $(this).attr("price");
			var sellerCode = $(this).attr("sellerCode");
			sku.addExisted(pv, price, stock, sellerCode);
		});
		
		window.EditorObject = KindEditor.create('#txaItemDiscription', {
			items : [ 'source', '|', 'undo', 'redo', 'cut', 'copy', 'paste', '|',
				'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist',
				'insertunorderedlist', 'indent', 'outdent', '|', 'selectall', '|', 'fontname', 'fontsize',
				'|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'removeformat', '/',
				'image', 'table', 'hr', 'emoticons', 'link', 'unlink' ],
			width : 680,
			minHeight : 195,
			filterMode : true,//过滤html代码
			resizeType : 1, //2或1或0，2时可以拖动改变宽度和高度，1时只能改变高度，0时不能拖动。
			uploadJson : '/image/uploadImage.htm?size=1024&type=1',
			fileManagerJson : '/images/getStorageFileInfoJsonActon.htm',
			categoryJson : '/images/getImagesCategoryJsonActon.htm',
			allowFileManager : true,
			afterChange : function(){
				setDisLength();
			}
		});
	});