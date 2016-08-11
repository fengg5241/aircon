/**
 * 
 */
package com.panasonic.b2bacns.bizportal.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Simanchal.Patra
 *
 */
@Component
@Configuration
public class GlobalTaskPool {

	private static final Logger LOG = Logger.getLogger(GlobalTaskPool.class);

	private static final int TASK_TERMINATION_WAIT_PERIOD = 5;

	private AtomicInteger maxConcurrentTaskCount;

	private ExecutorService executorPool;

	@Value("${pf.op.concurrency.maxConcurrentTaskCount}")
	private String maxConcurrentTaskCountProp;

	@PostConstruct
	public void initialize() {

		this.maxConcurrentTaskCount = maxConcurrentTaskCountProp != null ? new AtomicInteger(
				Integer.parseInt(maxConcurrentTaskCountProp))
				: new AtomicInteger(Integer.MAX_VALUE);

		this.executorPool = new ThreadPoolExecutor(0,
				this.maxConcurrentTaskCount.intValue(), 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());

		LOG.warn(String.format(
				"Initialized Global taskPool with maximum thread size [%s] ",
				maxConcurrentTaskCountProp));
	}

	public ExecutorService getGlobalTaskPool() {
		return this.executorPool;
	}

	public int getMaxConcurrentTaskCount() {
		return this.maxConcurrentTaskCount.get();
	}

	public void executeTask(final Runnable task) {
		this.executorPool.execute(task);
	}

	public synchronized void shutdown() throws InterruptedException {
		LOG.warn("Shutting down Global task pool");
		this.executorPool.shutdown();
		LOG.warn(String.format(
				"Waiting [%d seconds] for termination of all running tasks",
				TASK_TERMINATION_WAIT_PERIOD));
		this.executorPool.awaitTermination(TASK_TERMINATION_WAIT_PERIOD,
				TimeUnit.SECONDS);
		LOG.warn("Global task pool Shutdown successfully");
	}
}
