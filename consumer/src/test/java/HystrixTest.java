import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.ArrayList;
import java.util.Collection;
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

        context = HystrixRequestContext.initializeContext();
        Future<String> f1 = new MyHystrixCollapser("zhangsan").queue();

        Thread.sleep(3000);

        Future<String> f2 = new MyHystrixCollapser("zhangsan123").queue();
        Thread.sleep(1000);

        Future<String> f3 = new MyHystrixCollapser("zhangsan1234").queue();
        Future<String> f4 = new MyHystrixCollapser("zhangsan12345").queue();
        System.out.println(f1.get() + "=" + f2.get() + f3.get() + f4.get());
        context.shutdown();

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


    static class MyHystrixCollapser extends HystrixCollapser<List<String>, String, String> {

        private final String name;

        public MyHystrixCollapser(String name) {
            this.name = name;
        }

        @Override
        public String getRequestArgument() {
            return name;
        }

        @Override
        protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, String>> requests) {
            return new BatchCommand(requests);
        }

        @Override
        protected void mapResponseToRequests(List<String> response, Collection<CollapsedRequest<String, String>> requests) {
            int count = 0;

            for (CollapsedRequest<String, String> request : requests) {
                request.setResponse(response.get(count++));
            }
        }

        private static final class BatchCommand extends HystrixCommand<List<String>> {

            private final Collection<CollapsedRequest<String, String>> requests;

            public BatchCommand(Collection<CollapsedRequest<String, String>> requests) {
                super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")).andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
                this.requests = requests;
            }

            @Override
            protected List<String> run() throws Exception {
                System.out.println("真正执行请求......");
                List<String> response = new ArrayList<>();
                for (CollapsedRequest<String, String> request : requests) {
                    response.add("返回结果" + request.getArgument());
                }
                return response;
            }
        }

    }
}
