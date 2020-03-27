/*定义一个全局变量，命名为DREAM*/
var DREAM = {
	/*属性：checkLogin，值是一个方法，用来检测是否已经登录*/
	checkLogin : function(){
		//先从cookie中得到TOKEN
		var token = $.cookie("DREAM_TOKEN");
		//如果值不存在--没有登陆或者已经过时
		if(!token){//!没有token
			return ;
		}

		$.ajax({
			url : "http://localhost:8088/user/token/" +token,
			type : "GET",
			dataType : "jsonp", //跨域请求的格式设置为jsonp
			success : function(data){ //DreamResult
				if(data.status == 200){ //找到了登陆账号
					//得到账号 DreamResult.data.username tbUser.username
					var username = data.data.username;
					//把username回显到原来登录和注册的位置
					$("#loginbar").html("<a href='#'>"+username+"</a>,您好!欢迎来到Dream商城!");
				}
			}
		});
	}
}
/*JSON的跨域操作，根据token来得到当前的用户*/
$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	//先从cookie中得到TOKEN
	DREAM.checkLogin();
});