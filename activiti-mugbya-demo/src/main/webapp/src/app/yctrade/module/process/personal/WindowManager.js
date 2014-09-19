/**
 * Created by mugbya on 14-9-9.
 */
Ext.define("YCTrade.module.process.personal.WindowManager",{
    extend: 'Ext.window.Window',
    width: 330,
    height: 220,
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
//        this.MemberFormPanel = Ext.create('YCTrade.module.process.personal.FormPanel');
        this.MemberFormPanel = Ext.create('YCTrade.module.process.start.FormPanelManager');
        this.createFbar();
        this.reasonWindow();
        this.window.on('ensure', this.handlerEnsure, this);
    },

    createFbar: function() {
        this.fbar = Ext.create('Ext.toolbar.Toolbar', {
            items: ['->', {
                text: '同意',
                handler: function() {
                    this.fireEvent('agree',{
                        value : true,
                        reason : null
                    });
                },
                scope: this
            }, {
                text: '驳回',
                handler: function() {
                    this.initreasonWindow();
                    //this.handlerEnsure();
//                    var reason = this.getReason();
//                    this.fireEvent('reject',{
//                        data : reason
//                    });
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

    initreasonWindow : function(){
        this.window.items.items[0].reset();
        this.window.show();
    },

    getReason : function () {
      return this.window.items.items[0].getValue();
    },

    reasonWindow : function () {
        this.window = Ext.create('Ext.window.Window',{
            width: 330,
            height: 150,
            title : '驳回理由',
            closable: true,
            modal: true,
            closeAction: 'close',
            titleCollapse: true,
            border: false,
            bodyPadding: 15,
            pageY: 50,
            pageX: document.body.clientWidth / 2 - 420 / 2,
            items : [{
                xtype     : 'textareafield',
                grow      : true,
                width     : 280,
                anchor    : '80%'
            }],
            fbar : [{
                text: '确认',
                handler: function() {
                    /**
                     * 还需验证reason是否填写
                     */
                    console.log(this.getReason());
                    var reason = this.getReason();
                    this.window.fireEvent('ensure',{
                        data : reason
                    });
                },
                scope: this
            },{
                text: '取消',
                handler: function() {
                    this.window.close();
                },
                scope: this
            }]
        });
    },

    handlerEnsure : function(params){
        //console.log(params);
        this.fireEvent('reject',{
            reason : params.data,
            value : false
        });
    }
});