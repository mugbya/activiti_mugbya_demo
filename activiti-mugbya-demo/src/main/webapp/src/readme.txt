
##############################################
                    说明
##############################################
注意，一定不要用id
具体请参考src/app/ycui/main/WorkspacePanel.js中的注释


##############################################
                    目录划分
##############################################

app/                前端代码目录

app/ycui                        公共框架代码
app/ycui/common                 公共组件
app/ycui/main                   主界面代码
app/ycui/utils                  使用工具类 

app/yctrade                     川金项目代码
app/yctrade/module              模块代码路径

api/                            本地ajax测试数据
api/module/getModules.json      模块加载数据，定义了哪些模块被加载到主界面
                                数据为树形组织，isModule标识了是否是模块，还是分类节点
                                id为具体模块的包路径，例如：YCTrade.module.user.UserManager

lib/                            第三方组件路径


##############################################
                   开发场景
##############################################

===============
公共框架开发
------------
工作目录位于app/ycui

===============
模块开发
-------------
工作目录位于app/yctrade
例如模块test，名称为YCTrade.module.test.JustTest
1、创建对应文件yctrade/module/test/JustTest.js
2、编写代码，涉及到的附属UI都集中放到test目录下
    例如要实现一个Form，代码文件可以为yctrade/module/test/JustTestForm.js
    这样可以方便模块的管理以及插拔
3、涉及到的ajax模拟数据，放到api/test/getTestData.json内
    代码中通过YCUI.utils.Configuration.getAPI('test.getTestData')来获得对应路径
4、将模块加入到主界面
    将模块名添加到/api/module/getModules.json中
