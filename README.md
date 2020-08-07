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
                   
        
2.OkHttp的理解(2020.8.5)
    1.创建一个Chain,然后进入proceed函数，然后获取到下一个拦截器，调用拦截器的intercept函数,得的Chain,再次调用proceed函数，直到最后一次
    new RealInterceptorChain
    chain.proceed(request) ---> new RealInterceptorChain
                                获取interceptor
 （RetryAndFollowUpInterceptor) interceptor.intercept(chain) --->  chain.proceed(request) ---> new RealInterceptorChain
                                return response                                                获取interceptor
    return response(①)                                                                       （BridgeInterceptor) interceptor.intercept(chain)  ---> ... 
                                                                                               return response   
    2.拦截器对chain进行拦截，主要是对request 和 response 做一些操作  
    3.重连机制 RetryAndFollowUpInterceptor
      设置网络需要的header BridgeInterceptor
      get请求才能被缓存 CachedInterceptor【利用DiskLruCache将响应结果存入本地】
      RealConnction建立socket和tls连接 ConnectionInterceptor
      向服务器发送请求并返回Response对象  CallServerInterceptor
    4.并发执行数 和 当前域名请求数 判断将call放入到runningAsyncCalls或者 readyAsyncCalls 
      疑问：当前域名请求数如何判断的 ❎
      
        
    参考文档：
         https://www.jianshu.com/p/3f181c43b42b  
         https://blog.csdn.net/wanniu/article/details/80742404 (拦截器 CachedInterceptor)
         https://www.jianshu.com/p/93dae81077a1 (调度器 Dispatcher)
 
 3.Retrofit的理解(2020.8.6)
    1.内部网络请求是okhttp完成，它仅仅负责网络请求接口的封装
    2.类的职责
     CallAdapter.Factory是接口，有3个实现类
        DefaultCallAdapterFactory ExecutorCallAdaterFatory RxJava2CallAdapterFatory
        Android平台默认的是DefaultCallAdapterFactory，这个在PlatForm中预先定义了
     Call.Factory 是接口
        OkHttpClient
     Retrofit.Call是接口
        OkHttpCall   
     Converter.Factory是接口
        BuiltInConvertersFactory OptionalConverterFatory... 
     Call是retrofit2接口   
        ExecutorCallbackCall
     3.在Retrofit的build函数中，构建了整体所需的一些对象，比如url，callAdapter等  


























