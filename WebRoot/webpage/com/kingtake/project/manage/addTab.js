function addTab(subtitle, url, icon) {
        var jq = top.jQuery;
        var progress = $("div.messager-progress");
        if (progress.length) {
            return;
        }
        rowid = "";
        $.messager.progress({
            text : loading,
            interval : 200
        });
        if (!jq('#maintabs').tabs('exists', subtitle)) {
            //判断是否进行iframe方式打开tab，默认为href方式
            if (url.indexOf('isHref') != -1) {
                jq('#maintabs').tabs('add', {
                    title : subtitle,
                    href : url,
                    closable : true,
                    icon : icon
                });
            } else {
                jq('#maintabs').tabs(
                        'add',
                        {
                            title : subtitle,
                            content : '<iframe src="' + url
                                    + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
                            closable : true,
                            icon : icon
                        });
            }
            $.messager.progress('close');
        } else {
            jq('#maintabs').tabs('select', subtitle);
            $.messager.progress('close');
        }
        tabClose();
    }

    function tabClose() {
        var jq = top.jQuery;
        /* 双击关闭TAB选项卡 */
        jq(".tabs-inner").dblclick(function() {
            var subtitle = $(this).children(".tabs-closable").text();
            jq('#tabs', parent.document).tabs('close', subtitle);
        });
        /* 为选项卡绑定右键 */
        jq(".tabs-inner").bind('contextmenu', function(e) {
            jq('#mm').menu('show', {
                left : e.pageX,
                top : e.pageY
            });

            var subtitle = jq(this).children(".tabs-closable").text();

            jq('#mm', parent.document).data("currtab", subtitle);
            // $('#maintabs').tabs('select',subtitle);
            return false;
        });
    }