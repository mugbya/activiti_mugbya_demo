Ext.Loader.setConfig({
    enabled: true,
    paths: {
        /*
         * 设置YCUI以及YCTrade命名空间对应的路径
         */
        YCUI: "src/app/ycui",
        YCTrade: "src/app/yctrade"
    }
});

Ext.application({
    name: 'YCApp',
    start: function() {
        /*
         * 启动主界面
         */
        Ext.create('YCUI.main.MainPanel', {
            title: '四川金融资产交易所 - 业务管理系统'
        });
    },
    launch: function () {
        /*
         * 初始化会执行这里，加载Configuration后，启动界面
         */
        Ext.require('YCUI.utils.Configuration', this.start, this);
    }
});
