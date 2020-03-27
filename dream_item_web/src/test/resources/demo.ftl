<html>
<head>
    <title>freemarker测试</title>
    <meta charset="UTF-8">
</head>
    freemarker模板
    <br>
    msg:${msg}
    <br>
    TbItem的title:${tbItem.title}
    <#--
    for(TbItem item:items){
    }
    -->
    <br>
    <#list items as item>
        ${item_index}-->${item.title} <br>
    </#list>
    <#--遍历map-->
    <#list itemMap?keys as key>
        ${key}-->${itemMap[key].title} <br>
    </#list>
    <#--if-->
    <#list items as item>
        <#--不展示奇数行，展示偶数行-->
        <#if item_index%2=0>
            偶数:${item_index}--> ${item.title}
        <#else >
            奇数：
        </#if>
        <br>
    </#list>
    日期：${date?date} <br>
    时间：${date?time} <br>
    日期时间：${date?datetime} <br>
    以上都是默认格式，自定义格式：${date?string("yyyy/MM/dd HH:mm:ss")}
</html>