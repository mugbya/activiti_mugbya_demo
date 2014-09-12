/**
 * Created by mugya on 14-8-23.
 */

Ext.define("YCTrade.module.process.start.StartProcess",{
    extend: 'Ext.panel.Panel',
    layout: 'border',
    title: '会员申请',

    initComponent: function () {

        this.initCompo();

        Ext.applyIf(this, {
            layout: 'border',
            items: [this.form],
            tbar: this.tbar
        });
        this.callParent(arguments);
    },

    initCompo : function () {
        this.createForm();
        this.initTbar();
    },

    createForm : function () {
        this.form = Ext.create("YCTrade.module.process.start.FormPanelManager");
    },

    initTbar : function () {
        this.tbar = Ext.create('Ext.toolbar.Toolbar', {
            items: [ {
                text: '保存',
                handler: function() {
                       // this.fireEvent('saveMember');
                    this.saveMember();
                    //var record = this.addMemberFormPanel.getForm().getValues();
//					this.fireEvent('saveMember', {
//						data: record
//					});
                },
                scope: this
            }, {
                text: '重置',
                handler: function() {
                    this.addMemberFormPanel.getForm().reset();
                },
                scope: this
            }, {
                text: '关闭',
                handler: function() {
                    this.close();
                },
                scope: this
            }]
        });
    },

    saveMember : function () {
        this.form.form.submit({
            //url: 'process/start.json',
            url : 'member/start.json',
            waitTitle: '提示',
            method: 'POST',
            waitMsg: '正在处理数据,请稍候...',
            success: function (form, action) {
                console.log('成功');
                form.reset();

                Ext.MessageBox.alert('提示', '<center>流程启动 ---成功！</center>');
            },
            scope: this,
            failure: function (form, action) {
                console.log('失败');
                Ext.MessageBox.alert('提示', '<center>流程启动 ---失败！</center>');
            }
        });
    }
    
    

});