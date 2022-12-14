$(document).ready(function () {
    // 加载搜索选项
    getRepository();
    // 加载列表
    //getTransList();
    // 按钮绑定
    bindButton();
});

function getRepository() {
    $.ajax({
        type: 'GET',
        async: false,
        url: '/sys/repository/findRepList.do',
        data: {},
        success: function (data) {
            var list = data.result;
            for (var i = 0; i < list.length; i++) {
                $("#repositoryId").append('<option value="' + list[i].id + '">' + list[i].repName + '</option>');
            }
            if (list.length > 0) {
                var id = list[0].id;
                $("#repositoryId").val(id);
                getTransList(id);
            }
        },
        error: function () {
            alert("请求失败！请刷新页面重试");
        },
        dataType: 'json'
    });
}

function getTransList(id) {
    var $table = $('#transList');
    $table.bootstrapTable({
        url: '/sys/repository/findRepTreegridById.do?id=' + id,            //请求后台的URL（*）
        method: 'GET',            //请求方式（*）
        toolbar: '#toolbar',        //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        //queryParams: queryParams,//传递参数（*）
        /*sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1.html,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [5, 25, 50, 100],        //可供选择的每页的行数（*）*/
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: false,                //是否启用点击选中行
        // height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        smartDisplay: false,
        detailView: false,                   //是否显示父子表
        responseHandler: function (res) {
            return res.result;
            /*return {
                "total": res.result.totalElements,//总页数
                "rows": res.result.content//数据列表
            };*/
        },
        columns: [
            {
                field: 'id',
                title: '转换编号'
            }, {
                field: 'text',
                title: '脚本名称'
            }, {
                field: 'extra',
                title: '脚本路径'
            }, {
                field: 'operate',
                title: '操作',
                width: 200,
                align: 'center',
                valign: 'middle',
                formatter: actionFormatter
            }],
        //在哪一列展开树形
        treeShowField: 'text',
        //指定父id列
        parentIdField: 'pid',
        onResetView: function (data) {
            //console.log('load');
            $table.treegrid({
                initialState: 'collapsed',// 所有节点都折叠
                // initialState: 'expanded',// 所有节点都展开，默认展开
                treeColumn: 1,
                // expanderExpandedClass: 'glyphicon glyphicon-minus', //图标样式
                // expanderCollapsedClass: 'glyphicon glyphicon-plus',
                onChange: function () {
                    $table.bootstrapTable('resetWidth');
                }
            });

            //只展开树形的第一级节点
            $table.treegrid('getRootNodes').treegrid('expand');

        }
    });
}

function actionFormatter(value, row, index) {
    if (row.transStatus === 1) {
        return [
            '<a class="btn btn-danger btn-xs" id="stop" type="button" data-id="' + row.id + '"><i class="fa fa-stop" aria-hidden="true"></i>&nbsp;停止</a>'
        ].join('');
    } else if (row.transStatus === 2) {
        return [
            '<a class="btn btn-primary btn-xs" id="start" type="button" data-id="' + row.id + '"><i class="fa fa-play" aria-hidden="true"></i>&nbsp;启动</a>',
            '&nbsp;&nbsp;',
            '<a class="btn btn-primary btn-xs" id="edit" type="button" data-id="' + row.id + '"><i class="fa fa-edit" aria-hidden="true"></i>&nbsp;编辑</a>',
            '&nbsp;&nbsp;',
            '<a class="btn btn-primary btn-xs" id="delete" type="button" data-id="' + row.id + '"><i class="fa fa-trash" aria-hidden="true"></i>&nbsp;删除</a>'
        ].join('');
    } else {
        return [
            '<a class="btn btn-primary btn-xs" id="edit" type="button" data-id="' + row.id + '"><i class="fa fa-edit" aria-hidden="true"></i>&nbsp;编辑</a>',
            '&nbsp;&nbsp;',
            '<a class="btn btn-primary btn-xs" id="delete" type="button" data-id="' + row.id + '"><i class="fa fa-trash" aria-hidden="true"></i>&nbsp;删除</a>'
        ].join('');
    }
}

function search() {
    $('#transList').bootstrapTable('refresh');
}

function transNameFormatter(value, row, index) {
    if (value.length > 15) {
        var newValue = value.substring(0, 14);
        return newValue + "……";
    } else {
        return value;
    }
}

function bindButton() {
    // 编辑
    $('#transList').delegate('#edit', 'click', function (e) {
        var $target = $(e.currentTarget);
        var transId = $target.data('id');
        location.href = "/web/trans/edit.shtml?transId=" + transId;
    });

    // 删除
    $('#transList').delegate('#delete', 'click', function (e) {
        var $target = $(e.currentTarget);
        var transId = $target.data('id');
        layer.confirm('确定删除该单位？', {
                btn: ['确定', '取消']
            },
            function (index) {
                layer.close(index);
                $.ajax({
                    type: 'DELETE',
                    async: true,
                    url: '/sys/trans/delete.do',
                    data: {
                        "id": transId
                    },
                    success: function (data) {
                        if (data.success) {
                            location.replace(location.href);
                        } else {
                            layer.alert(data.message, {icon: 5});
                        }
                    },
                    error: function () {
                        layer.alert("系统出现问题，请联系管理员");
                    },
                    dataType: 'json'
                });
            },
            function () {
                layer.msg('取消操作');
            }
        );
    });

    // 单个任务启动
    $('#transList').delegate('#start', 'click', function (e) {
        var $target = $(e.currentTarget);
        var transId = $target.data('id');
        layer.confirm(
            '确定启动该转换？',
            {btn: ['确定', '取消']},
            function (index) {
                layer.close(index);
                $.ajax({
                    type: 'GET',
                    async: true,
                    url: '/sys/trans/startTrans.do?id=' + transId,
                    data: {},
                    success: function (data) {
                        if (data.success) {
                            location.replace(location.href);
                        } else {
                            layer.alert(data.message, {icon: 5});
                        }
                    },
                    error: function () {
                        alert("系统出现问题，请联系管理员");
                    },
                    dataType: 'json'
                });
            },
            function () {
                layer.msg('取消操作');
            }
        );
    });

    // 单个任务停止
    $('#transList').delegate('#stop', 'click', function (e) {
        var $target = $(e.currentTarget);
        var transId = $target.data('id');
        layer.confirm(
            '确定停止该转换？',
            {btn: ['确定', '取消']},
            function (index) {
                layer.close(index);
                $.ajax({
                    type: 'GET',
                    async: true,
                    url: '/sys/trans/stopTrans.do?id=' + transId,
                    data: {},
                    success: function (data) {
                        if (data.success) {
                            location.replace(location.href);
                        } else {
                            layer.alert(data.message, {icon: 5});
                        }
                    },
                    error: function () {
                        alert("系统出现问题，请联系管理员");
                    },
                    dataType: 'json'
                });
            },
            function () {
                layer.msg('取消操作');
            }
        );
    });

}

