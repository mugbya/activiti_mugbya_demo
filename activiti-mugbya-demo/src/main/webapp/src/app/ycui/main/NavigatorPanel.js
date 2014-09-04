/*
 * 导航区域界面
 */
Ext.define('YCUI.main.NavigatorPanel', {
    extend: 'Ext.tree.Panel',
    //rootVisible:false,  // 隐藏root节点
    requires: [
        'YCUI.main.model.Module'
    ],
    initComponent: function () {
        /*
         * 代码注意逻辑分离，不要揉到一堆
         * 一个函数内的代码行数尽量精简，同时逻辑功能尽量独立
         */
        var store = this.createStore();

        Ext.applyIf(this, {
            store: store,
            rootVisible: false,
            listeners: {
                itemclick: function (store, record) {
                    if (record.data.isModule == true) {
                        console.log(record.data.id + "--");
                        /*
                         * 触发一个自定义事件
                         * 模块之间，尽量通过事件（而不是回调）来减低耦合
                         */
                        this.fireEvent('moduleSelected', {
                          moduleId: record.data.id
                          // moduleId: record.get('id')
                        });
                    }
                },
                scope: this
            }
        });
        this.callParent(arguments);
    },
    createStore: function() {
        var store = Ext.create('Ext.data.TreeStore', {
            model: 'YCUI.main.model.Module',
            proxy:{
                type: 'ajax',
                /*
                 * 通过Configuration的getAPI来获取ajax的地址
                 * 本地调试的时候，可以在对应的目录下放入json文件，做回环测试
                 */
                url: YCUI.utils.Configuration.getAPI('module.getModules'),
                reader: {
                    type: 'json',
                    root: 'data'       
                }
            },
            root: {
                text: '流程中心',  // 根节点
                expanded: true
            }
        });
        return store;
    }
});
