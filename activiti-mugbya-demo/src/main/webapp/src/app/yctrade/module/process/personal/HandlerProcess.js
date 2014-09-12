/**
 * Created by mugya on 14-8-25.
 */
Ext.define("YCTrade.module.process.personal.HandlerProcess", {
    extend: 'Ext.panel.Panel',
    layout: 'border',
    title: '个人代办',
    rec : 'none',
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

    initCompo: function () {
        this.initManagerGrid();
        this.windowMember = Ext.create("YCTrade.module.process.personal.WindowManager");
        this.windowMember.on('agree', this.agreeHandler, this);
    },

    initManagerGrid : function () {
        this.managerGrid = Ext.create('YCUI.common.GridPanel', {
            region: 'center',
            dataModel: 'YCTrade.module.process.personal.model.UserTask',
            //api: 'member.getMembers',
            //api: 'process/tasklist.json',
            api: 'member/tasklist.json',
            frame: true,
            forceFit: true,
            columns: [{
                text: '任务编号',
                dataIndex: 'taskId',
                flex: 1
            }, {
                text: '申请人',
                dataIndex: 'applyUser',
                flex: 1
            }, {
                text: '审批人',
                dataIndex: 'assignee',
                flex: 1
            },{
                text: '操作',
                align: 'center',
                renderer: this.operate,
                flex: 1,
                scope : this     // 注意指定这个作用域，解藕后对后面有影响
            }
            ]
        });
    },

    operate: function (value, meta, rec) {
        var id = Ext.id();

//        注释的都有问题
//        this.createGridButton.defer(1, this, ['办理',  meta, rec, id]);
//        Ext.defer(this.createGridButton(value, meta, rec, id),50, this);

        var assignee = rec.data.assignee;

        Ext.defer(function() {
            Ext.widget('button', {
                renderTo: id,
                text: '办理',
                scale: 'small',
                handler: function() {
                    this.initWindowMember(rec, assignee);
                    this.saveRec(rec);
                },
                scope : this
            });
        }, 50, this);    // 这里也需要注意作用域

        return Ext.String.format('<div id="{0}"></div>', id);
    },

    /**
     * 其实主要把这个独立出来，后续需要绑定事件
     */
    initWindowMember : function(rec, assignee){
        console.log(rec);
        console.log(assignee );
        this.windowMember.setTitle('流程办理[' + assignee + "审批]");
        //this.windowMember.MemberFormPanel.getForm().reset();
        this.windowMember.MemberFormPanel.getForm().loadRecord(rec);
        this.windowMember.show();
    },

    saveRec : function(rec){
        this.rec = rec;
    },

    getRec : function () {
        return this.rec;
    },

    agreeHandler : function () {
        console.log("回调函数");
        var rec = this.getRec();
        console.log(rec);
        //console.log(taskId);
        Ext.Ajax.request({
            url: 'member/handler.json',
            //url : 'process/handler.json',
            params: {
                taskId: rec.data.taskId,
                userId : rec.data.assignee
            },
            success: function(response){
                console.log("成功-----");
                this.managerGrid.store.reload();
                this.windowMember.close();
            },
            scope : this
        });
    }

});