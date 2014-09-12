/**
 * Created by mugbya on 14-9-9.
 */
Ext.define("YCTrade.module.process.personal.WindowManager",{
    extend: 'Ext.window.Window',
    width: 380,
    height: 230,
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
            items: [this.MemberFormPanel],
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
            items: ['->', {
                text: '同意',
                handler: function() {
                    this.fireEvent('agree');
                },
                scope: this
            }, {
                text: '驳回',
                itemId:'memberWinRst',
                handler: function() {
                   // this.MemberFormPanel.getForm().reset();
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