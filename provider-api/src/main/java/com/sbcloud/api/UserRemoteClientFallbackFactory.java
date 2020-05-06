package com.sbcloud.api;

import com.sbcloud.domain.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserRemoteClientFallbackFactory implements FallbackFactory<UserRemoteClient> {
	@Override public UserRemoteClient create(Throwable throwable) {
		log.error("UserRemoteClient回退：");
		return new UserRemoteClient() {

			@Override public String addUser(User user) {
				return "addUser-error";
			}

			@Override public String hello() {
				return "hello-error";
			}

			@Override public String timeout(Integer t) {
				return "timeout-error";
			}
		};
	}
}
