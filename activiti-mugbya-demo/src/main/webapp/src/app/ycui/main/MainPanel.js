/*
 * App 主界面
 */
Ext.define('YCUI.main.MainPanel', {
	extend: 'Ext.container.Viewport',
    layout: 'border',
    /*
     * initComponet可以视作初始化方法
     * 派生自ExtJS UI类的子类，都可以通过重载此方法，来进行初始化工作
     * 在initComponet调用之前，外面传入的参数实际apply到当前对象了，因此可以直接通过this获取
     * 例如下面的 html: this.title，这里的title由外接传入
     */
    initComponent: function () {
        //左侧导航区域
        this.navigatorPanel = Ext.create('YCUI.main.NavigatorPanel', {  // 原本可以写在外面，涉及作用域写 进来
            title: 'Activiti工作流',
            region: 'west',
            width: 250,
            split: true,
            collapsible: true
        });

        this.navigatorPanel.on('moduleSelected', this.moduleSelected, this);  // 绑定事件
 
        //右侧工作区
        this.workspacePanel = Ext.create('YCUI.main.WorkspacePanel', {
            region: 'center'
        });


        Ext.applyIf(this, {
            items: [{
                region: 'north',
                xtype: 'panel',
                padding: 10,
                height: 55,
//                html: this.title,
                tbar:[{text:'工作流 - activiti 学习', xtype:'label'},
                    '->',{
                    text:'退出',
                    handler:function(){
                        Ext.Ajax.request({
                            url: '/user/destory.json',
                            success: function (response) {
                                window.location.href="/login.jsp";
                            }
                        });
                    }
                }]

            }, this.navigatorPanel, this.workspacePanel]
        });

        /*
         * 由于是重载Ext.container.Viewport的initComponet，因此，需要调用父类的此方法
         * this.callParent，实际就是调用调用了Ext.container.Viewport的initComponent方法
         */
        this.callParent(arguments);
    },
    /*
     * 处理模块选中事件，类似监听
     */
    moduleSelected: function(params) {
        this.workspacePanel.openModule(params.moduleId);
    }
});
