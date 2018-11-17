//跨域请求设置类型为jsop
$(function () {
    $.ajax({
        url:"http://localhost:8084/sso/islogin",
        dataType:"jsonp"
    });
})
//登陆后拿到信息，设置商城头部，为空就使用默认值
function callback(data) {
    if(data!=null){
        $("#pid").html(data.username+"您好，欢迎来到<b>ShopCZ商城</b>&nbsp;&nbsp;&nbsp;<a href='http://localhost:8084/sso/logout'>注销</a>");
    }else{
        $("#pid").html("您好，欢迎来到<b>ShopCZ商城</b>[<a href=\"javascript:login();\">登录</a>][<a href=\"\">注册</a>]");
    }
}
function login() {
    //设置当前页面去登陆页面的编码及字符
    var backUrl = location.href;
    backUrl=encodeURI(backUrl);
    backUrl = backUrl.replace("&","~");
    //跳转到登陆的页面
    location.href="http://localhost:8084/sso/tologin?backUrl=" + backUrl;

}