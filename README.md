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
        f.HandlerThread介绍和使用
            1.本质是Thread,在其run方法中帮助我们定义了prepare 和 looper操作，同时也定义了当前线程的Handler 和 线程的优先级
        g.IntentService介绍和使用
            1.是封装的HandlerThread的写法，相当于一个整体部分中有Thread线程，想象是模块类
            2.每调用一次，就构造message将其消息放入在子线程的队列中，排队执行
        h.AsyncTask介绍和使用  
            1.线程池 + Handler ，执行方法execute默认线程池，executeOnExecutor指定线程池
            2.cancel只是给定一个标志，还需要通过isCancled手动去结束任务
            3.默认的执行是串行，想并行的话，那么不使用原本提供的队列，直接使用线程池即可
    4.参考文档
        1.https://blog.csdn.net/iispring/article/details/47180325（handler）
        2.https://blog.csdn.net/yanbober/article/details/45936145(handler)   
        3.https://blog.csdn.net/qq_25806863/article/details/72782050(asynctask) 
        4.https://blog.csdn.net/javazejian/article/details/52426425(intentservice)
                   
        
2.混淆的理解(2020.8.4)
    通用模板 + 依赖的库 + 项目中的类
    


























