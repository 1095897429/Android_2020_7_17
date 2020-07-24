# Android_2020_7_17

1.Handler理解(2020.7.23)
    1.基本使用
        一个线程中只能有一个Looper，只能有一个MessageQueue，可以有多个Handler，多个Messge    
    2.基本原理
    3.常见问题
        a.next方法阻塞式等待获取Message？
        b.消息是如何放置在MessageQueue中？ (系统开机时间 + 延迟毫秒数)   
        c.一个Activity有多个Handler时，如何确保正确接收？
            msg.target = this --> 指定了消息的目标
        d.looper的两个退出方法
            quitSafely 清空延迟消息，发送非延迟消息 
            quit 清空所有消息队列中的消息
        e.ThreadLocal / 同步消息、同步屏障    
            某些数据以线程为作用域，不同线程具有不同数据副本，采用它
            防止内存溢出，我们可以在使用完threadLocal后，调用remove操作 (担心线程会一直运行，然后value的值一直没有办法释放) 
            每个线程都有一个threadlocalmap,存储的key是threadlocal,value是传入的值，
        
2. 




























