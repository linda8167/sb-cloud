import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * @author 庞飞
 * @date: 2020/4/28 14:02
 * @description TODO
 */
public class HystrixTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //System.out.println(Thread.currentThread().getName());
        //String result = new MyHystrixCommand("zhangsan").execute();
        //System.out.println(result);
        //Future<String> future = new MyHystrixCommand("zhangsan").queue();
        //System.out.println(future.get());


        //IntStream.rangeClosed(1, 20).forEach(i -> {
        //    String c = new MyHystrixCommand("zhangsan", HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE).execute();
        //    System.out.println(c);
        //});

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future> list = new ArrayList<>();
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        System.out.println(Thread.currentThread().getName());
        IntStream.rangeClosed(1, 16).forEach(i -> {

            String tname = Thread.currentThread().getName();
            Future<String> c = new MyHystrixCommand("zhangsan", HystrixCommandProperties.ExecutionIsolationStrategy.THREAD).queue();
            list.add(c);
            // System.out.println(tname + "=>" + Thread.currentThread().getName());
            // MyHystrixCommand.flushCache("zhangsan");
        });
        for (Future f : list) {
           f.get();
        }
        context.shutdown();
        //IntStream.rangeClosed(1, 20).parallel().forEach(i -> {
        //    String tname = Thread.currentThread().getName();
        //    executorService.submit(() -> {
        //        String c = new MyHystrixCommand("lisi", HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE).execute();
        //        System.out.println(tname + "=>" + Thread.currentThread().getName() + "=>" + c);
        //    });
        //});


    }

    static class MyHystrixCommand extends HystrixCommand<String> {

        private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("MyKey");

        private String name;

        public MyHystrixCommand(String name) {
            super(HystrixCommandGroupKey.Factory.asKey("MyGroup"));
            this.name = name;
        }

        public MyHystrixCommand(String name, HystrixCommandProperties.ExecutionIsolationStrategy strategy) {
            super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("MyGroup")).andCommandKey(GETTER_KEY).andCommandPropertiesDefaults(
                    HystrixCommandProperties.Setter().withExecutionIsolationStrategy(strategy)
            ).andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(10)
                    .withMaxQueueSize(0).withMaximumSize(15)));
            this.name = name;
        }

        @Override
        protected String run() throws Exception {
            // Thread.sleep(1000 * 10);
            System.out.println(Thread.currentThread().getName() + "=>run");
            return Thread.currentThread().getName();
        }

        @Override
        protected String getCacheKey() {
            return String.valueOf(this.name);
        }

        public static void flushCache(String name) {
            HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(name);
        }

        @Override
        protected String getFallback() {
            return Thread.currentThread().getName() + "失败了";
        }
    }
}
