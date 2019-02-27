var table = null, treeGrid = null,layer = null;
var path = uri.root();
console.log(path);
layui.use(['table', 'treeGrid', 'layer'], function () {
    table = layui.table;
    layer = layui.layer;
    treeGrid = layui.treeGrid;

    // 左侧表格
    table.render({
        elem: '#budgetTemplateTable',
        url:  path + 'TpmBudgetController.do?getLeft',
        cols: [[
            {
                field:'CATEGORY_CODE',
                title: '类型编码',
                width: '29%',
                align: 'left'
            },
            {
                field:'CATEGORY_NAME',
                title: '预算类型名称',
                width: '70%',
                align: 'left'
            }
        ]],
        parseData: function(res){
        	//选中第一行数据进行左右联动
            if(res.data.length > 0) {
                renderTable({CATEGORY_CODE: res.data[0].CATEGORY_CODE});
            }
        },
        id: 'budgetTemplateTable',
        text: {
            none: '暂无相关数据'
        },
        height: 'auto'
    });
    
    //数据加载后，默认选中第一行的背景色
    setTimeout(function(){
        $(".budgetTemplateTable .layui-table-body tr[data-index=\"0\"]").addClass('layui-table-click');
    }, 100);

    // 渲染树表格
    var renderTable = function (params) {
        //layer.load(2);
        treeGrid.render({
            id: 'budgetInfoTable',
            elem: '#budgetInfoTable',
            idField:'ID', //必須字段
            url:path + 'TpmBudgetController.do?getTemplate',
            treeId:'ID',//树形id字段名称
            treeUpId:'PID',//树形父id字段名称
            treeShowName:'DETAIL_NAME',//以树形式显示的字段
            height: 'auto',
            iconOpen:true, //是否显示图标【默认显示】
            isOpenDefault:true, //节点默认是展开还是折叠【默认展开】
            method:'post',
            cols: [[
                {type: 'numbers', width: 50, align: 'left', title: '序号'},
                {field: 'DETAIL_NAME', minWidth: 200, align: 'left', title: '预算明细名称', edit : true},
               // {field: 'CATEGORY_CODE', width: 100, align: 'left', title: '预算类型ID',edit : true,hide:true},
                {field: 'CATEGORY_CODE_DTL', width: 80, align: 'left', title: '编码', edit : true},
                {field: 'DETAIL_SYMBOL', width: 60, align: 'left', title: '标识', edit: 'text'},
                {field: 'DETAIL_DESC', title: '描述说明', align: 'left', edit: 'text'},
                {width: 120, title: '操作', align:'left', templet: function(d) {
                        var add = '<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="add">添加</a>';
                        var del = '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
                        return add + del;
                    }},
            ]],
            where: params,
            isPage: false,
            text: {
                none: '暂无相关数据'
            },
            done: function () {
                layer.closeAll('loading');
            },
            onClickRow:function (index, o) {
                console.log(index,o,"单击！");
            },
            onDblClickRow:function (index, o) {
                console.log(index,o,"双击");
            }
        });
    };

    renderTable();

    //监听枚举类型、名称行单击事件
    table.on('row(budgetTemplateTable)', function(obj){
        let params = {
        	CATEGORY_CODE : obj.data.CATEGORY_CODE,
        };
        renderTable(params);
        //切换点击的行（背景色）
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });

    treeGrid.on('tool(budgetInfoTable)',function (obj) {
        if(obj.event === 'del'){ // 删除行
            layer.confirm("你确定删除数据吗？如果存在下级节点则一并删除，此操作不能撤销!",
                {icon: 3, title:'提示'},
                function(index){ // 确定回调
                    obj.del();
                    layer.close(index);
                },function (index) { // 取消回调
                    layer.close(index);
                }
            );
        }else if(obj.event==="add"){//添加行
            add(obj)
        }
    });
});

var i = 1000;
function add(pObj) {
    var pdata=pObj ? pObj.data : null;
    var param={};
    param.DETAIL_NAME = '明细预算名称';
    param.ID = String(++i);
    param.PID = pdata ? pdata.ID : "0";
    treeGrid.addRow('budgetInfoTable', pdata ? pdata[treeGrid.config.indexName] + 1 : 0, param);
}


function save() {
    var data = treeGrid.getDataList('budgetInfoTable');
    if(data.length > 0) {
        $.ajax({
            url : "/save",
            type : "post",
            data : {data: data},
            success : function(r) {
                if (r.code === 0) {
                    layer.msg(r.msg);
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }else{
                    layer.msg(r.msg);
                }
            }
        });
    } else {
        layer.msg('请添加数据')
    }
}