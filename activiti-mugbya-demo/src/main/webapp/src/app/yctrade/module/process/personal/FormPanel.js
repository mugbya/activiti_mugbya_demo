/**
 * Created by mugbya on 14-9-16.
 */
Ext.define('YCTrade.module.process.personal.FormPanel', {
    extend: 'Ext.form.FormPanel',
    defaultType: 'textfield',
    labelAlign: 'right',
    labelWidth: 65,
//	frame: false,
    border:false,
    bodyStyle: 'padding:5 5 0',
    initComponent: function() {

        this.initCompo();

        Ext.applyIf(this, {
            items: [{
                fieldLabel: '会员ID',
                labelAlign: 'right',
                name : 'memberid',
                //hidden : true,
                allowBlank : true
            },{
                fieldLabel: '会员名称',
                labelAlign: 'right',
                anchor: '80%',
                name: 'membername',
            }, //this.memberdeptCombo,
                {
                    fieldLabel: '会员电话',
                    labelAlign: 'right',
                    anchor: '80%',
                    name : 'memberphone'
                } ,{
                    fieldLabel: '电子邮箱',
                    labelAlign: 'right',
                    anchor: '80%',
                    name: 'memberemail',
                }
            ]
        });
        this.callParent(arguments);
    },

    initCompo: function() {
        this.memberdeptCombo = this.createMemberdeptCombo();
        this.membertypeCombo = this.createMembertypeCombo();
    },

    createMemberdeptCombo: function() {
        var userdeptCombo = Ext.create('Ext.form.ComboBox', {
            fieldLabel: '所属部门',
            name: 'deptid',
            displayField: "deptname",
            valueField: "deptname",
            editable: false,
            typeAhead: true,
            anchor: '80%',
            labelAlign: 'right',
            //emptyText: '请选择...',
            triggerAction: 'all',
            data : ["qqq","wwww"]
        });
        return userdeptCombo;
    },

    createMembertypeCombo: function() {
        var membertypeCombo = Ext.create('Ext.form.ComboBox', {
            fieldLabel: '会员类型',
            name: 'rolename',
            displayField: "rolename",
            valueField: "Id",
            editable: false,
            typeAhead: true,
            anchor: '80%',
            labelAlign: 'right',
            //emptyText: '请选择...',
            triggerAction: 'all',
            data : ["111","222"]
        });
        return membertypeCombo;
    }


})