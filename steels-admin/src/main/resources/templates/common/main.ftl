<#macro layout title>

    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8">
        <title>基于 layui 的极简社区页面模版</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="keywords" content="fly,layui,前端社区">
        <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
        <link rel="stylesheet" href="/static/layui/css/layui.css">
        <#--<link rel="stylesheet" href="res/css/global.css">-->
    </head>
    <body>
    131232141341431151
    <#nested >

    <script src="/static/layui/layui.js"></script>
    <#--<script>-->
        <#--layui.cache.page = '';-->
        <#--layui.cache.user = {-->
            <#--username: '游客'-->
            <#--,uid: -1-->
            <#--,avatar: '../res/images/avatar/00.jpg'-->
            <#--,experience: 83-->
            <#--,sex: '男'-->
        <#--};-->
        <#--layui.config({-->
            <#--version: "3.0.0"-->
            <#--,base: '../res/mods/' //这里实际使用时，建议改成绝对路径-->
        <#--}).extend({-->
            <#--fly: 'index'-->
        <#--}).use('fly');-->
    <#--</script>-->

    <#--<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_30088308'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "w.cnzz.com/c.php%3Fid%3D30088308' type='text/javascript'%3E%3C/script%3E"));</script>-->
    <script src="/static/jQuery/jQuery-2.2.0.min.js"></script>
    </body>
    </html>
</#macro>
