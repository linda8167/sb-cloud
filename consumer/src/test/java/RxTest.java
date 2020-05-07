import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author 庞飞
 * @date: 2020/4/29 11:59
 * @description TODO
 */
public class RxTest {

    public static void main(String[] args) {
        String s = new CommandHelloWorld("world").execute();
        System.out.println(s);


        Observable<String> ho = new CommandHelloWorld("zs").observe();
        Subscription subscription1 = ho.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("a1 " + s + " " + Thread.currentThread().getName());
            }
        });
        Subscription subscription2 = ho.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted " + s + " " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError " + s + " " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext " + s + " " + Thread.currentThread().getName());
            }
        });


        subscription1.unsubscribe();
        ho.single();
        // BlockingObservable<String> bob = ho.toBlocking();

        System.out.println(ho.single());

    }


    static final class CommandHelloWorld extends HystrixCommand<String> {

        private final String name;

        public CommandHelloWorld(String name) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()));
            this.name = name;
        }

        @Override
        protected String run() throws Exception {
            // Thread.sleep(5000);
            return "hello " + name + " !";
        }

        @Override
        protected String getFallback() {
            System.out.println("失败啦");
            return "失败啦";
        }
    }


    static final class ObCommandHelloWorld extends HystrixObservableCommand<String> {

        private final String name;

        public ObCommandHelloWorld(HystrixCommandGroupKey group, String name) {
            super(group);
            this.name = name;
        }

        @Override
        protected Observable<String> construct() {
            return Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    if (!subscriber.isUnsubscribed()) {

                        subscriber.onNext("Hello ");

                        subscriber.onNext(name + "!");

                        subscriber.onCompleted();

                    }

                }
            }).subscribeOn(Schedulers.io());
        }
    }

}
