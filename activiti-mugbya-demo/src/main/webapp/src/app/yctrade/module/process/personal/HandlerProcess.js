/**
 * Created by mugya on 14-8-25.
 * 参考链接： http://www.techmix.net/2010/11/25/add-button-to-extjs-gridpanel-cell-using-renderer/
 */
Ext.define("YCTrade.module.process.personal.HandlerProcess", {
    extend: 'Ext.panel.Panel',
    layout: 'border',
    title: '个人代办',
    rec: 'none',
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
        this.windowMember.on('reject', this.rejectHandler, this);
        this.windowWithReason= Ext.create("YCTrade.module.process.personal.WindowWithReason");
        this.windowWithReason.on('stop', this.stopHandler, this);
        this.windowWithReason.on('reSubmit', this.reSubmitHandler, this);
    },

    initManagerGrid: function () {
        this.managerGrid = Ext.create('YCUI.common.GridPanel', {
            region: 'center',
            dataModel: 'YCTrade.module.process.personal.model.UserTask',
            //api: 'member.getMembers',
            //api: 'process/tasklist.json',
            api: 'member/tasklist.json',
            frame: true,
            forceFit: true,
            columns: [
                {
                    header: '当前流程节点',
                    dataIndex: 'taskName',
                    flex: 1
                },
                {
                    header: '申请人',
                    dataIndex: 'applyUser',
                    flex: 1
                },
                {
                    header: '审批人',
                    dataIndex: 'assignee',
                    flex: 1
                },
                {
                    xtype: 'actioncolumn',
                    header: '操作',
                    align: 'center',
                    items: [
                        {
                            icon: 'src/lib/button.png',
                            tooltip: '办理',
                            scope: this,
                            handler: this.operate
                        }
                    ],
                    flex: 1,
                    scope: this     // 注意指定这个作用域，解藕后对后面有影响
                }
            ]
        });
    },

    operate: function (grid, rowIndex, colIndex, item, e, record) {

        console.log(record);

        var reason = record.data.reason;

        if(reason != null && record.data.taskName == "調整申请"){
            this.initWindowWithReason(record);
        }else{
            this.initWindowMember(record);
        }

        this.saveRec(record);
    },

    initWindowWithReason : function (record) {
        console.log(record);
        this.windowWithReason.MemberFormPanel.getForm().reset();
        this.windowWithReason.setTitle('流程办理[' + record.data.taskName + " ]");
        //.setValue(record.data.reason);
        console.log(this.windowWithReason);
        console.log(this.windowWithReason.items.items[0]);
        this.windowWithReason.items.items[0].setValue(record.data.reason);
        this.windowWithReason.MemberFormPanel.getForm().loadRecord(record);
        this.windowWithReason.show();
    },

    /**
     * 其实主要把这个独立出来，后续需要绑定事件
     */
    initWindowMember: function (record) {
        console.log(record);
        this.windowMember.MemberFormPanel.getForm().reset();
        this.windowMember.setTitle('流程办理[' + record.data.taskName + " ]");
        this.windowMember.MemberFormPanel.getForm().loadRecord(record);
       // this.windowMember.MemberFormPanel.getForm().setDisabled(true);
        this.windowMember.show();
    },

    saveRec: function (rec) {
        this.rec = rec;
    },

    getRec: function () {
        return this.rec;
    },

    /**
     * 同意
     */
    agreeHandler: function () {
        var rec = this.getRec();
        Ext.Ajax.request({
            url: 'member/handler.json',
            //url : 'process/handler.json',
            params: {
                taskId: rec.data.taskId,
                userId: rec.data.assignee,
                variables : true
                //reason : null
            },
            success: function (response) {
                this.managerGrid.store.reload();
                this.windowMember.close();
            },
            scope: this
        });
    },

    /**
     * 驳回
     * @param params
     */
    rejectHandler : function (params) {
        var reason = params.data;
        var rec = this.getRec();
        Ext.Ajax.request({
            url: 'member/handler.json',
            //url : 'process/handler.json',
            params: {
                taskId: rec.data.taskId,
                userId: rec.data.assignee,
                variables : false,
                reason : reason
            },
            success: function (response) {
                this.managerGrid.store.reload();
                this.windowMember.window.close();
                this.windowMember.close();
            },
            scope: this
        });
    },

    /**
     * 重新申请
     */
    reSubmitHandler : function () {
        var rec = this.getRec();
        this.windowWithReason.MemberFormPanel.submit({
            url : 'member/reSubmit.json',
            params: {
                taskId: rec.data.taskId,
                userId: rec.data.assignee,
                variables : true
            },
            waitTitle: '提示',
            method: 'POST',
            waitMsg: '正在处理数据,请稍候...',
            success: function (form, action) {
                console.log('成功');
                this.managerGrid.store.reload();
                this.windowWithReason.close();
                Ext.MessageBox.alert('提示', '<center>提交 ---成功！</center>');
            },
            scope: this,
            failure: function (form, action) {
                console.log('失败');
                Ext.MessageBox.alert('提示', '<center>提交 ---失败！</center>');
            }
        });
    },

    /**
     * 停止流程
     */
    stopHandler : function () {
        var rec = this.getRec();
        Ext.Ajax.request({
            url: 'member/handlerNoReason.json',
            //url : 'member/revision.json',
            params: {
                taskId: rec.data.taskId,
                userId: rec.data.assignee,
                variables : false
            },
            success: function (response) {
                this.managerGrid.store.reload();
                this.windowWithReason.close();
            },
            scope: this
        });
    }

});