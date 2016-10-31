# AsyncTaskFunctional
AsyncTask的函数式封装
Android官方有段介绍
https://developer.android.com/reference/android/os/AsyncTask.html
>AsyncTask enables proper and easy use of the UI thread. This class allows you to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers.
大概意思是AsyncTask允许更容易地使用UI线程。此类可以在无需操作线程的情况下操作UI线程。

随着Android的发展现在AsyncTask基本已成过去式了。今天的主角并不是AsncTask，仅仅通过官方这一类来讲解一种封装思路。希望通过这一思路抛砖引玉。
####首先我们编写一个工具类
```java
AsyncTaskUtils.java
public class AsyncTaskUtils {
	public static <T> void doAsync(final IDataCallBack<T> callBack) {
		new AsyncTask<Void, Void, T>() {

			protected void onPreExecute() {
				callBack.onTaskBefore();
			};

			@Override
			protected T doInBackground(Void... params) {
				// TODO
				return callBack.onTasking(params);
			}

			protected void onPostExecute(T result) {
				callBack.onTaskAfter(result);
			};

		}.execute();
	}
}
```

这个工具类有一个泛型T也就是当AsyncTask后台执行完后返回回来的对象，这里我们使用的是接口回调的方式，工具类doAsync(final IDataCallBack<T> callBack) 方法的参数是一个callBack对象，这样的设计比较常见。Android底层源码setOnClickListener()方法就是这一原理。这里不展开讲。
####接着我们编写回调的接口对象
```java
public interface IDataCallBack<T> {
	/**任务执行之前*/
	void onTaskBefore();

	/**任务执行中...*/
	T onTasking(Void... params);

	/**任务执行之后*/
	void onTaskAfter(T result);
}
```
如上图所示，接口对应上述对AsyncTask的封装的三个方法编写了三个方法，onTaskBefore()、Tasking(Void... params)、onTaskAfter(T result)。用于上述方法的回调。
这样我们基本完成了对AsyncTask的封装。
看我们调用的地方
```java
AsyncTaskUtils.doAsync(new IDataCallBack<String>() {

					@Override
					public void onTaskBefore() {

					}

					@Override
					public String onTasking(Void... params) {
						
					}

					@Override
					public void onTaskAfter(String result) {

					}
				});
```

我们对比之前的方式，在没封装之前我们每次都得new一个AsyncTask对象或者extend继承这一类来做操作。细心的同学可能会发现说到底你仅仅只是封装了一层根本就没简便操作啊。是的。看下面。看我们的BaseActivity
```java
BaseActivity.java
public class BaseActivity extends Activity {

	public <T> void doAsync(IDataCallBack<T> callBack) {
		AsyncTaskUtils.doAsync(callBack);
	}
}

```
调用的地方
```java
MainActivity.this.doAsync(new IDataCallBack<String>() {

					@Override
					public void onTaskBefore() {

					}

					@Override
					public String onTasking(Void... params) {
						String result = "";
						try {
							DefaultHttpClient httpClient = new DefaultHttpClient();
							HttpGet get = new HttpGet("http://www.baidu.com");
							HttpResponse response = httpClient.execute(get);
							if (response.getStatusLine().getStatusCode() == 200) {
								result = EntityUtils.toString(response.getEntity());
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return result;
					}

					@Override
					public void onTaskAfter(String result) {
						System.out.println(result);
					}
				});
```
上面只是调用的地方。这样一来我们所有的网络都统一到一个地方了。这也就是为什么在上面我把 AsyncTask<Void, Void, T>()中的第三个参数使用泛型的原因了。在我们编写界面的时候难免会出现一个以上的请求，为了保证代码的健壮性，减少代码的冗余度。所有的网络请求都统一走这一个地方了。

#lambda表达式
---
接下来是我们的主角，lambda表达式。Lambda表达式是Java SE 8中引入的一个重要的新特性。lambda表达式允许你通过表达式来代替功能接口。
官方链接：http://www.developer.com/java/start-using-java-lambda-expressions.html
以前我们我们在给View设置点击事件的时候代码是这样的。
```java
view.setOnClickListener(new OnClickListener() {
	@Override
	public void click(View view) {
		//方法内容
	}
})
```
而当我们在使用了lambda表达式后点击事件可以这样写。
```java
view.setOnclickListener(view->{//方法内容});
```
是不是很简单。在Android Studio2.2之前虽然jdk可以使用jdk8但是对lambda却支持不是很好，当然可以引入RetroLambda Plugin来支持。随着Android Studio2.1的发布google官方终于原生支持lambda了。
不可否认使用lambda表达式使我们的代码更加简洁了，但也有人说使用了lambda表达式使我们的代码可读性降低了。
话说回来，lambda表达式作为一个新的事物。在大家都未接受的时候肯定认可他的人觉得好用漂亮。而不认可的人或者是没有入门的人觉得不好用，还看不懂就想当年Window Xp刚出来的时候大家都觉得Window 2000才是王道，XP系统那么花哨好看不好用。甚至有人还把XP的主题给换回98那种样式。扯远了。正是因为有人觉得这种new Interface的方式不好用。所以才会有lambda这种新的事物出来。lambda表达式最早是出现在Scala语言中。他允许通过函数式的方式来调用方法。可能因为Oracle官方发现了这一简便性，所以才有了jdk8中的lambda表达式。相信在不久的将来lambda表达式会变得越来越流行，肯定会有越来越多的人使用这一表达式。尤其搭配上当下热门的RxJava、Retrofit等技术。
