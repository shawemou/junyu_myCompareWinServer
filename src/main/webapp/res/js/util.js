//生成GUID类似的随机数
function newGuid() {
	var guid = "";
	for ( var i = 1; i <= 32; i++) {
		var n = Math.floor(Math.random() * 16.0).toString(16);
		guid += n;
		if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
			guid += "-";
	}
	return guid;
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function utilCallBack(data, textStatus) {
	if (textStatus != "success") {
		alert("请求错误！");
		return undefined;
	}
	if (data === "ERROR") {
		alert("后台发生错误！");
		return undefined;
	}
	try {
		var myobj = eval('(' + data + ')');
	} catch (Error) {
		alert("数据转换失败,格式不正确！");
		return undefined;
	}
	if (myobj == undefined) {
		alert("未获取到数据！");
		return undefined;
	}
	if(myobj["sessionState"] == 0){
		window.location.href = "login.html?data=" + newGuid();
		return undefined;
	}
	return myobj;
}