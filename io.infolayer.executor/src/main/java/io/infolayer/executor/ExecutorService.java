package io.infolayer.executor;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.infolayer.entity.PluginCall;
import io.infolayer.exception.PluginException;
import io.infolayer.executor.listener.SimpleLogListener;
import io.infolayer.executor.util.OsgiUtils;
import io.infolayer.plugin.IRunnableListener;
import io.infolayer.plugin.IRunnablePlugin;
import io.infolayer.plugin.OutputFlow;
import io.infolayer.plugin.PluginPrototype;
import io.infolayer.plugin.PluginSubmissionResponse;

@Component(immediate = true, service = IPluginExecutorService.class)
public class ExecutorService implements IPluginExecutorService {

	private static Logger log = LoggerFactory.getLogger(ExecutorService.class);

	private KafkaClient client;

	private PoolExecutorService threadPool = null;
	private Map<String, PluginPrototype> pluginCache = null;
	private String workingDirectory;

	public ExecutorService() {
		this.pluginCache = new ConcurrentHashMap<String, PluginPrototype>();
		this.threadPool = new PoolExecutorService(10, 100);
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	
	@Activate
	public void start(BundleContext ctx) throws Exception {
		log.info("Starting Infolayer ExecutorService");
		client = new KafkaClient("kafka:9029", "xpto", "topic");
        //client.listen();
	}
	
	@Deactivate
	public void stop(BundleContext ctx) {
		
		client.close();
		this.pluginCache.clear();
		threadPool.shutdown();
		log.info("Infolayer ExecutorService stopped.");
	}

	public int getQueueCount() {
		return this.threadPool.getRunningCount();
	}
	
	public Set<String> getActiveQueue() {
		return this.threadPool.getRunningInstances();
	}

	public boolean cancel(String executionEventID) {
		return this.threadPool.cancel(executionEventID);
	}

	public PluginSubmissionResponse submit(PluginCall call, Map<String, String> environment) throws PluginException {
		
		long tStart = System.currentTimeMillis();
		try {

			OutputFlow flow = OutputFlow.newInstance(this.workingDirectory);
			
			if (call == null) {
				throw new PluginException("Call cannot be null");
			}
			
			if (flow == null) {
				throw new PluginException("OutputFlow cannot be null");
			}
			
			PluginPrototype prototype =  this.pluginCache.get(call.getPluginName());
			
			if (prototype == null) {
				throw new PluginException(MessageFormat.format("Plugin not found: {0}", call.getPluginName()));
			}
			
			Map<String, String> env = prototype.cloneEnvironment();
			if (environment != null) {
				env.putAll(environment);
			}
			
			Set<IRunnableListener> pluginListeners = new HashSet<IRunnableListener>();
			
			//Enforce Logs
			IRunnableListener logListener = new SimpleLogListener();
			pluginListeners.add(logListener);
			
			IRunnablePlugin runnablePlugin = this.createRunnablePlugin(prototype);
			
			runnablePlugin.submit(call, env, pluginListeners, flow);
			
			Future<IRunnablePlugin> future = this.threadPool.submit(runnablePlugin);
			PluginSubmissionResponse response = new PluginSubmissionResponse(future);
			
			log.info(MessageFormat.format("Plugin submission: Instance {0}, params {1}, environment {2}, elapsed {3} ms.", 
					runnablePlugin.getInstanceID(), 
					call.getPluginParams() != null ? call.getPluginParams().size() : -1, 
					env != null ? env.size() : -1,
					(System.currentTimeMillis() - tStart)));
			
			return response;
			
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
			}
			log.error(MessageFormat.format("Plugin submission exception: Plugin name {0}, params {1}, elapsed {2} ms.", 
					call.getPluginName(), 
					call.getPluginParams() != null ? call.getPluginParams().size() : -1, 
					(System.currentTimeMillis() - tStart)));
			throw new PluginException(e);
		}

	}

	private IRunnablePlugin createRunnablePlugin(PluginPrototype prototype) throws Exception {
		
		IRunnablePlugin runnablePlugin = OsgiUtils.createInstance(IRunnablePlugin.class, this.getClass(), prototype.getPluginClassName());
		
		runnablePlugin.configure(
				prototype.clonePlugin(), 
				prototype.getTimeout(), 
				null, //FIXME prototype.cloneInputHandlers(this.getClass()), 
				null); //FIXME prototype.cloneOutputHandlers(this.getClass())
		
		return runnablePlugin;
		
	}
	
}
