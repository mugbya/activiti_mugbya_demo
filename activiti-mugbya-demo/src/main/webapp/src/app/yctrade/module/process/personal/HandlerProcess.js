/**
 * Created by mugya on 14-8-25.
 */
Ext.define("YCTrade.module.process.personal.HandlerProcess",{
    extend: 'Ext.panel.Panel',
    layout: 'border',
    title: '个人代办',
    requires: [
        'YCTrade.module.process.personal.model.UserTask'
    ],
    initComponent: function () {

        this.initCompo();

        Ext.applyIf(this, {
            layout: 'border',
            items: [this.managerGrid]
        });

        this.callParent(arguments);
    },

    initCompo : function () {
        this.managerGrid = Ext.create('YCUI.common.GridPanel', {
            region: 'center',
            dataModel: 'YCTrade.module.process.personal.model.UserTask',
            //api: 'member.getMembers',
            api: 'process/tasklist.json',
            frame: true,
            forceFit: true,
            columns: [{
                    text: '任务编号',
                    dataIndex: 'id',
                    flex: 1
                },{
                    text: '申请人',
                    dataIndex: 'username',
                    flex: 1
                },{
                    text: '审批人',
                    dataIndex: 'assignee',
                    flex: 1
                },{
                    text: '操作',
                    align: 'center',
                    renderer: function(val,meta,rec) {
                        // generate unique id for an element
                        var id = Ext.id();

                        //传入的三个参数可以自己进行调试，其中有当前点击该行的record
                        var taskid = rec.id;
                        var assignee = rec.data.assignee;
                        var store = this.store;

                        Ext.defer(function() {
                            Ext.widget('button', {
                                renderTo: id,
                                text: '办理',
                                scale: 'small',
                                handler: function() {
                                    Ext.Ajax.request({
                                        url: 'process/handler.json',
                                        params: {
                                            taskId: taskid,
                                            userId : assignee
                                        },
                                        success: function(response){
                                            store.reload();
                                        }
                                    });
                                }
                            });
                        }, 50);
                        return Ext.String.format('<div id="{0}"></div>', id);
                    },
                    flex: 1
                }
            ]
        });
    }
});