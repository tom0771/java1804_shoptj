<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>静态页面测试</title>
</head>
<body>
    <hr>
    ${key}!!!

    <hr/>
        编号 -- 主题   --   内容   --   价格 <br/>
    <#list goods as gs>

        ${gs.id} -- ${gs.title} -- ${gs.ginfo} -- ${gs.price}<br/>
        ----------------------------------------------------------
    </#list>
    时间：
    ${time?date}<br/>

    <hr/>
    ${time?string("yyyy-MM-dd HH:mm:ss")}


</body>
</html>