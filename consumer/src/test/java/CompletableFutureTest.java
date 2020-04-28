import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author 庞飞
 * @date: 2020/4/28 17:09
 * @description TODO
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        System.out.println("开始");
        new Thread(()-> {
            try {
                Thread.sleep(5000);
                completableFuture.complete("123");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println(completableFuture.get());
        System.out.println("结束");
    }
}
