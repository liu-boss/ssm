<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id='loadingDiv' style="position: absolute; z-index: 1000; top: 0px; left: 0px;width: 100%; height: 100%; background: #fff; text-align: center;">
    <h4 style="top: 48%; position: relative;">
        <font color="#15428B">努力加载中···</font>
    </h4>
</div>
<script type="text/JavaScript">
    function closeLoading() {
        $("#loadingDiv").fadeOut("normal", function () {
            $(this).remove();
        });
    }
    let no;
    $.parser.onComplete = function () {
        if (no) clearTimeout(no);
        no = setTimeout(closeLoading, 500);
    }
</script>
