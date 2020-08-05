
1.纯Java代码编写

2.在github上创建一个仓库，在本地也创建了一个项目
    本地项目 add commit后，关联远程仓库后，拉取时发现 显示 fatal: refusing to merge unrelated histories
    原因：两个仓库 是属于 两个独立的仓库
    解决：命令就是 $git pull origin master --allow-unrelated-histories
         在sourceTree中操作：用变基代替合并 打钩 即可

3.学习下插件化
    1.微信的Tinker

4.网络部分
    1.封装retrofit请求框架
    2.rxJava + retrofit 防止内存泄露
        方便性：autoDispose > rxLifecycler > 手动切断disposable

7.23功能
1.弱handler添加
2.通过hide/show的方式 切换的时候走onHiddenChanged()生命周期
3.状态栏适配

8.4 滑动
























