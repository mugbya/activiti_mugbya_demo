/**
 * Created by mugbya on 14-9-9.
 */
Ext.define("YCTrade.module.process.personal.WindowWithReason",{
    extend: 'Ext.window.Window',
    width: 330,
    height: 320,
    resizable: false,
    draggable: true,
    closable: true,
    modal: true,
    closeAction: 'close',
    collapsible: true,
    titleCollapse: true,
    buttonAlign: 'right',
    border: false,
    animCollapse: true,
    bodyPadding: 15,
    pageY: 50,
    pageX: document.body.clientWidth / 2 - 420 / 2,
    constrain: true,
    maximizable: false,

    initComponent: function(){

        this.initCompo();

        Ext.applyIf(this, {
            items: [{
                xtype     : 'textareafield',
                grow      : true,
                width     : 280,
                anchor    : '80%',
                readOnly  : true,   // 禁止写入
                disabled  : true   // 变灰
            },this.MemberFormPanel],
            fbar: this.fbar
        });
        this.callParent(arguments);
    },

    initCompo: function() {
        this.MemberFormPanel = Ext.create('YCTrade.module.process.start.FormPanelManager');
        this.createFbar();

    },

    createFbar: function() {
        this.fbar = Ext.create('Ext.toolbar.Toolbar', {
            items: [{
                text: '取消申请',
                handler: function() {
                    this.fireEvent('stop');
                    console.log("取消申请");
                },
                scope: this
            },'->', {
                text: '提交',
                handler: function() {
                    this.fireEvent('reSubmit');
                    console.log("提交");
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
    }
});
