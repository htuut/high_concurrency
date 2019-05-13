## 并发
### 1、jmeter 的使用
com.htuut.jmeter

序号 |备注|操作 
---|---|---
1 | 添加线程组| 右键 TestPlan->Add->Threads->Thread Group
2 | 添加 http 请求| 右键 新添加的线程组->Add->Sampler
3 | 添加结果树|右键 新建的 http 请求->Add->Listener->View Results Tree
3 | 添加结果图|右键 新建的 http 请求->Add->Listener->Graph Results
### 2、线程安全性
#### 1、原子性：提供了互斥访问，同一时刻只能有一个线程来对它进行操作
#### 2、可见性：一个线程对主内存的修改可以及时的被其他线程观察到
#### 3、有序性：一个线程观察其他线程中的执行执行顺序，由于执行重排的存在，该观察结果一般杂乱无章
