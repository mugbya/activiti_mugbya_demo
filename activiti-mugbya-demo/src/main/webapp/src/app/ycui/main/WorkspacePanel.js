/*
 * 工作区域界面
 */
Ext.define('YCUI.main.WorkspacePanel', {
    extend: 'Ext.tab.Panel',
    /*
     * 尽量不要使用魔数，也不要使用零散的字符串
     */
    idPrefix: 'workspace_',                      // 这个应该是定义的属性吧
    initComponent: function () {
        Ext.applyIf(this, {                             //如果指定对象不存在相同的属性，将配置的属性拷贝到指定对象
            /*
             * -- id: 'MainTabPanel',
             * 由于ExtJS的开发习惯是高度模块化，重用度很高，因此一定不要使用id，否则会出现DOM ID污染
             * 也就是说不要通过id来标示、查找对象，尽量使用对象本身
             * 对于items一类的“无对象引用”的元素，使用itemId来标示
             * 总之，一定不要哦使用id
             */
            items: []
        });
        this.callParent(arguments);
    },
    getWorkspaceId: function(moduleId) {
        return this.idPrefix + moduleId
    },
    getWorkspace: function(moduleId) {
        var workspaceId = this.getWorkspaceId(moduleId);
        /*
         * itemId对getComponent查找是有效的
         */
        var workspace = this.getComponent(workspaceId);
        return workspace;
    },
    openModule: function(moduleId) {
        var workspace = this.getWorkspace(moduleId);
        if(Ext.isDefined(workspace)) {

            /**
             * 处理增加后的store刷新
             * @type {.items.items|*}
             */
            var arrays = this.items.items;

            for(var i= 0;i < arrays.length; i++){
                if("workspace_YCTrade.module.process.personal.HandlerProcess" == arrays[i].itemId){
                    arrays[i].items.items[0].store.reload();
                }
            }

            this.setActiveTab(workspace); 
            return;
        }
        var workspaceId = this.getWorkspaceId(moduleId);
        var newWorkspace = Ext.create(moduleId, {
            closable : true,
            /*
             * 这里就是上面说的，用itemId来替代id
             */
            itemId: workspaceId
        });
        this.add(newWorkspace);
        this.setActiveTab(newWorkspace);
    }
});
