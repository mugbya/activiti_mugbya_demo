/**
* Demonstrates a simple login form.
*/
Ext.define('KitchenSink.view.form.LoginForm', {
    extend: 'Ext.form.Panel',
    xtype: 'form-login',

    url:"user/login.json",
    frame:true,
    width: 320,
    bodyPadding: 10,

    defaultType: 'textfield',

    items: [{
        id:'username',
        allowBlank: false,
        fieldLabel: '用户名',
        name: 'username',
        emptyText: 'user id',
        anchor: '80%',
        enableKeyEvents: true,
        keyup:function(textField, e){
            if(e.getKey() == 13){
                var form = this.up('form').getForm();
                if(form.isValid()){
                    form.submit({
                        waitMsg : '正在登录......',
                        success: function(form, action) {
                            window.location.href="/main.action";
                        },
                        failure: function(form, action) {
                            window.location.href="/login.jsp";
                        }
                    });
                }
            }
        }
    }, {
        id:'userpassword',
        allowBlank: false,
        fieldLabel: '密码',
        name: 'userpassword',
        emptyText: 'password',
        inputType: 'password',
        anchor: '80%',
        enableKeyEvents: true,
        listeners:{
            keyup:function(textField, e){
                if(e.getKey() == 13){
                    var form = this.up('form').getForm();
                    if(form.isValid()){
                        form.submit({
                            waitMsg : '正在登录......',
                            success: function(form, action) {
                                window.location.href="/main.action";
                            },
                            failure: function(form, action) {
                                window.location.href="/login.jsp";
                            }
                        });
                    }
                }
            }
        }
    }],

    buttons: [
        {
            text:'登陆',
            handler:function(){
                var form = this.up('form').getForm();
                if(form.isValid()){
                    form.submit({
                        waitMsg : '正在登录......',
                        success: function(form, action) {
                            window.location.href="/main.action";
                        },
                        failure: function(form, action) {
                            window.location.href="/login.jsp";
                        }
                    });
                }
            }
        },
        {
            text:'重置',
            handler: function () {
                this.up('form').getForm().reset();
            }
        }
    ],

    initComponent: function() {
        this.defaults = {
            labelWidth: 90
        };
        this.callParent();
    }
});

Ext.require([
    'Ext.form.*',
    'Ext.window.Window'
]);
Ext.onReady(function(){
    new Ext.window.Window({
        autoShow: true,
        title: 'Welcome To You',
        width: 400,
        height:200,
        minWidth: 300,
        minHeight: 200,
        layout: 'fit',
        plain: true,
        closable: false,
        collapsible:true,
        items:Ext.create("KitchenSink.view.form.LoginForm")
    });



});

