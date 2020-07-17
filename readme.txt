
1.纯Java代码编写

2.在github上创建一个仓库，在本地也创建了一个项目
    本地项目 add commit后，关联远程仓库后，拉取时发现 显示 fatal: refusing to merge unrelated histories
    原因：两个仓库 是属于 两个独立的仓库
    解决：命令就是 $git pull origin master --allow-unrelated-histories
         在sourceTree中操作：