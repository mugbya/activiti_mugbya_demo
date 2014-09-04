/*
 * 尽量把公用的部分抽离成可复用的类
 * ycui下面的就是公共框架
 * ycui/common下面就是公共组件
 */
Ext.define('YCUI.common.GridPanel', {
    extend: 'Ext.grid.Panel',
    layout: 'border',
    initComponent: function () {
        var store = this.createStore();
        Ext.applyIf(this, {
            store: store
        });
        this.store.load( );
        this.callParent(arguments);
    },
    createStore: function() {
        var store = Ext.create('Ext.data.Store', {
            model: this.dataModel,
            proxy: {
                type: 'ajax',
                //url: YCUI.utils.Configuration.getAPI(this.api),
                url : this.api,
                reader: {
                    type: 'json',
                    rootProperty: 'result'
                }
            }
        });
        return store;
    }
});
