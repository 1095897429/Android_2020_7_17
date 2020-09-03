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
    5.一般正常使用
      String url = "https://www.baidu.com"
      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
      Call call  = client.newCall(request);
      call.enqueue(new CallBack(){});
   
    参考文档：
         https://www.jianshu.com/p/3f181c43b42b  
         https://blog.csdn.net/wanniu/article/details/80742404 (拦截器 CachedInterceptor)
         https://www.jianshu.com/p/93dae81077a1 (调度器 Dispatcher)
 
 3.Retrofit的理解(2020.8.10)
    1.内部网络请求是okhttp完成，它仅仅负责网络请求接口的封装
    2.类的职责
     CallAdapter.Factory是接口 ：将响应类型 转化为 另一个类型
        DefaultCallAdapterFactory ExecutorCallAdaterFatory RxJava2CallAdapterFatory
        Android平台默认的是DefaultCallAdapterFactory，这个在PlatForm中预先定义了
     Call.Factory 是接口 ： 发送网络请求
        OkHttpClient()
     Retrofit2.Call是接口：默认返回的数据类型
        OkHttpCall   
     Converter.Factory是接口 : 添加对象转换器
        BuiltInConvertersFactory OptionalConverterFatory... 
     3.一般正常使用
        xxxService service = retrofit.create(xxxService.class);
        Call call = service.函数();
        call.enqueue(new CallBack(){});
     4.大体逻辑
          1.构建配置
          CallAdapted中的callAdapter.adapt(call) 默认调用了 DefaultCallAdapterFactory中的CallAdapter.adpt(call),创建ExecutorCallBackCall(executor,call),此call是在HttpServiceMethod类中invoke时创建的okHttpCall对象,call的类型是retrofit2.Call
          2.0 call 发送请求
          okHttpCall.enqueue请求中通过createRawCall函数通过callFactory(okHttpClient)的newCall获取到RealCall对象，类型是okhttp3.Call,retrofit模块将以前我们自动手写的call.enqueue函数帮助我们写了，【利用RealCall的enqueue函数调用】RealCall中的AsyncCall调用execute函数获取到response对象
          2.1 rx 发送请求
          将call请求转化为CallEnqueueObservable对象，在订阅时调用subscribeActual函数去调用call.enqueue(callback)函数
          3.异步处理 
          给定Callback,然后okHttp3获取到response后，在接口回调callback处理异步情况，最后在MainThreadExecutor去执行任务，通过handler在主线中去处理回调
          4.解析结果
          在OkHttpCall中的onResponse函数拿到后台未加工response对象数据，紧接着调用parseResponse(rawResponse)进行解析
     5.总结
        配置了一些url,解析器等等设置，然后在调用的时候利用okhttpCall对象，内部还是利用okhttpClient的RealCall对象去发起网络请求,okhttp的拦截器模式得以运用，最后拿到数据利用默认的executor处理
        如果设置了RxJava的话，那么在okhttpCall中的回调就到了CallEnqueueObservable中，然后观察者回调接口执行onNext(response)
   
     参考文档：
        https://segmentfault.com/a/1190000016835505（源码解析）
 
 4.RxJava2的理解(2020.8.10)
   1.一般正常使用
        RetrofitHelper.getApiService().getHomeData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
            .subscribe();
   2.subscribe()
     订阅开始时创建服务员，发送数据给服务员，服务员再将数据发送给观察者。在subscribe时将observer和emitter进行绑定关联
   3.subscribeOn(Schedulers.newThread()) 异步切换
     subscribeOn() 每次会返回一个 Observable
     创建NewThreadScheduler，调用父类的scheduleDirect()函数；创建NewThreadWork同时创建线程池，执行任务，这个任务就是new SubscirbeTask(parent)，parent是新创建的顾客！！！(parent持有上个顾客的引用)
     这样在新创建的Observable类中开启线程去执行【创建服务员 并且 传递数据的这个动作】
   4.多次调用subscribeOn()会出现什么情况？
     subscribeOn() 每次会返回一个 Observable
     可以理解为在线程1中创建线程2，在线程2中去执行【创建服务员 并且 传递数据的这个动作】，线程的话是【当前线程有效】-- 只有最先指定的有效
   5.observeOn(AndroidSchedulers.mainThread()) 异步切换
     observeOn() 每次会返回一个 Observable
     这里重新将observer封装了一下，此时新的observer是一个顾客 同时 也是个 Runnable 。在onNext方法中设置中通过Handler机制在运行Runnable任务，将数据传递给最原始的顾客
   6.多次调用observeOn()会出现什么情况？
     observeOn() 每次会返回一个 Observable
     指定承诺对【当前线程有效】 --- 只有最后指定的有效
   7.一些正常的操作符
     map :  就是重新将observer封装，在onNext方法中调用mapper.apply将其转化为另一个对象类型
     
  
   疑问：新创建的顾客中的队列如何读取数据的？ 
        在回调的时候创建了队列queue,然后在onNext中通过offer添加进去，之后再从队列中取出来
     
   总结：不断的创建顾客，最后由服务员将数据通过不同的顾客一层层传递到最原始的小明顾客
        一般的observer顾客都是按步传递，而observerOn中的将observer重新封装了一下，在onNext方法中设置了异步操作逻辑
    
    参考：
     https://www.jianshu.com/p/88aa273d37be (队列queue)     
     https://mp.weixin.qq.com/s/XWzBblkYsCa512a-6jyd6A (rxJava3)

 5.泛型的理解(2020.8.11 - 8.30 未解决)
    Type
    Class 是 Type 的子类
    TypeToken 内部有type rawType 两个类型
    
    Map<K,V> 参数化类型
    T[] 数组类型
    ========
    ① getActualTypeArguments() -- 获取类型内部的参数化类型 比如Map<K,V>里面的K，V类型
    ② getGenericSuperclass()   -- 获得带有泛型的父类
    泛型是一种"代码模块"
    泛型类：泛型定义在类上
    泛型接口：泛型定义在接口上
    泛型方法：泛型定义在方法上 <T>表示泛型方法
    泛型类派生：子类明确泛型类型参数/子类不明确泛型类型
    ？：类型通配符
    把子类对象直接赋值给父类引用 叫 向上转型
    把指向子类对象的父类引用赋值给子类 叫 向下转型
    擦拭法：工作都是编译器做的，虚拟机对泛型一无所知
    ①.刚开始进来的时候 EMPTY_OBJECT ，然后 检索到有双引号，设置DANGLING_NAME，在设置完 key后，设置NONEMPTY_OBJECT，如此循环
    ②.DANGLING_NAME 悬挂的键值对  
      
    参考：
        https://blog.csdn.net/u013673799/article/details/69663306 (gson的步步解析)
    
 6.文件操作以及图片加载(2020.8.17)  
    1.内部存储(data/data/包名/) 与 外部存储(storage/sdcard/Android/data/包名 || storage/0/sdcard/Android/data/包名)
    我们所说的Extenal Storage就是 sdcard下的目录
    2.文件的操作
        delete -- 删除目录是必须保证是空目录
        createNewFile -- 创建文件
        mkdir -- 创建目录
    3.DislruCache的理解
        
 7.Map是一些数据结构(2020.8.31)
    ① HashMap
        Java1.7 大体流程(头插法) -- 多线程死循环( 线程2导致了盖伦指向了石头，线程1在扩容时形成了(石头 - 盖伦 - 石头)互相指向，导致while一直循环)
        Java1.8 大体流程(尾插法)
        put get 遍历操作(遍历无序)
    ② LinkedHashMap
        在HashMap的原有的节点基础上新增了head,tail,before,after变量维护存储顺序,默认是插入顺序
        put(hashmap后的回调函数) get  remove 遍历操作(遍历有序，利用head的指向tail遍历)
        accessOrder --> true  是否基于访问排序 维护链表
        应用:LruCache(通过trimToSize来管理最近最少使用节点)
    ③ ConcurrentHashMap
        Java1.7 Segment数组 + HashEntry数组
        和HashMap明显的区别是key/value是不能为空
             
        
    
    
     















