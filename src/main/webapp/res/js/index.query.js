$(function() {
	query_AJAX();
	
	$("#userAddBtn").on('click', function() {
		window.location.href = "userAdd.html?data=" + newGuid();
	});
	
	$("#userQueryBtn").on('click', function() {
		query_AJAX();
	});
});

function query_AJAX() {
	$("#userQueryBtn").attr("disabled", true);
	$("#loading_Shadow").show();
	var param = {};
	param["name"] = encodeURI($("#name").val());
	param["login_name"] = encodeURI($("#login_name").val());
	param["gender"] = encodeURI($("#gender").val());
	param["id_number"] = encodeURI($("#id_number").val());
	param["busable"] = encodeURI($("#busable").val());
	
	param["page_no"] = encodeURI($("#page_no").val());
	param["random"] = newGuid();
	var url = "webUser?method=list&data=" + newGuid();
	$.post(url, param, function(data, textStatus) {
		$("#userQueryBtn").attr("disabled", false);
		queryUserCallBack(data, textStatus);
	});
	
}

function queryUserCallBack(data, textStatus) {
	$("#loading_Shadow").hide();
	var myobj = utilCallBack(data,textStatus);
	if (myobj != undefined) {
		$("#page_list").empty();
		$("#page_no_html").empty();
		var pageNo = myobj["page_no"];
		$("#page_no").val(pageNo);
		var itemCount = myobj["itemCount"];
		if (itemCount) {
			$("#page_list").html(printPage(itemCount,pageNo));
			$("#page_no_html").html(printPageNum(itemCount,pageNo));
			onChilkPage();
		}else{
			$("#page_no_html").html("共计0条")
		}
		$("#user_query tbody").empty();
		for ( var i in myobj["data"]) {
			var item = myobj["data"][i];
			var tdhtml = '<tr id="'+item["GUID"]+'">'
				+'<td>'+item["NAME"]+'</td>'
				+'<td>'+item["LOGIN_NAME"]+'</td>'
				+'<td>'+item["SECRET_KEY"]+'</td>'
				+'<td>'+gender(item["GENDER"]) + '</td>'
				+'<td>'+item["DEPARTMENT"]+'</td>'
				+'<td class=' + (item["BUSABLE"] == '0' ? 'red' : '') + '>'+busable(item["BUSABLE"])+'</td>'
				+'<td>详情</td>'
			+'</tr>';
			$("#user_query tbody").append(tdhtml);
		}
		$("#user_query tbody tr:even").addClass("alt");
		$("#user_query tbody tr").find("td:eq(6)").click(function() {
			var id = $(this).parent().attr("id");
			window.location.href = "userUpdate.html?id="+ encodeURI(id) + "&data=" + newGuid();
		}).mouseover(function() {
			$(this).css("color","red");
		}).mouseout(function() {
			$(this).css("color","");
		});
	}
}

function onChilkPage(){
	$("#fristPage").on('click', function() {
		$("#page_no").val("1");
		query_AJAX();
	});
	$("#endPage").on('click', function() {
		$("#page_no").val(endPage);
		query_AJAX();
	});
	$("#lastPage").on('click', function() {
		var page_no = parseInt($("#page_no").val());
		page_no = (page_no - 1) <= 0 ? 1 : (page_no - 1);
		$("#page_no").val(page_no);
		query_AJAX();
	});
	$("#nextPage").on('click', function() {
		var page_no = parseInt($("#page_no").val());
		page_no = (page_no + 1) > endPage ? endPage : (page_no + 1);
		$("#page_no").val(page_no);
		query_AJAX();
	});
	$("#page_next").keydown(function(e) {
		if(e.keyCode==13){
			var page_no = $("#page_next").val();
			if(!isNaN(page_no)){
				page_no = page_no > endPage ? endPage : page_no ;
				page_no = page_no <= 0 ? 1 : page_no ;
				$("#page_no").val(page_no);
				query_AJAX();
			}else{
				alert("页码请输入数字");
			}
		}
	});
	
}

function gender(id){
	if(id == "1"){
		return "男";
	}else if(id == "2"){
		return "女";
	}else{
		return "未知";
	}
}

function busable(id){
	if(id == "1"){
		return "启用";
	}else{
		return "停用";
	}
}