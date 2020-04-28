import com.google.common.collect.Lists;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import com.netflix.loadbalancer.reactive.ServerOperation;
import rx.Observable;

import java.util.List;

/**
 * @author 庞飞
 * @date: 2020/4/27 14:29
 * @description TODO
 */
public class RibbonTest {

    public static void main(String[] args) {

        List<Server> serverList = Lists.newArrayList(new Server("localhost", 8000), new Server("localhost", 8001));

        ILoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder().buildFixedServerListLoadBalancer(serverList);
        for (int i = 0; i < 1000; i++) {
            String result = LoadBalancerCommand.<String>builder().withLoadBalancer(loadBalancer).build().submit(new ServerOperation<String>() {
                @Override
                public Observable<String> call(Server server) {

                    return Observable.just(new String(server.getHost() + ":" + server.getPort()));
                }
            }).toBlocking().first();
            System.out.println(result);
        }
    }

}
