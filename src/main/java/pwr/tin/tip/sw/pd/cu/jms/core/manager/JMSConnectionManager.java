package pwr.tin.tip.sw.pd.cu.jms.core.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import pwr.tin.tip.sw.pd.cu.db.service.IUnitService;

@Component(value="jmsConnectionManager")
public class JMSConnectionManager {

	private final static Logger log = LoggerFactory.getLogger(JMSConnectionManager.class);

	@Autowired(required=true)
	private DefaultMessageListenerContainer workflowMessageContainer;
	
	@Autowired(required=true)
	private IUnitService unitService;

	public void stopConsumingMessagesFromWorkflow() {
		workflowMessageContainer.stop(); log.debug("Zatrzymanie pobierania komunikat�w z kolejki... {}", new Object[] {workflowMessageContainer.getDestinationName()});
		unitService.setOverload();
	}
	
	public void startConsumingMessagesFromWorkflow() {
		if (!workflowMessageContainer.isRunning()) {
			workflowMessageContainer.start(); log.debug("Wznowienie pobierania komunikat�w z kolejki... {}", new Object[] {workflowMessageContainer.getDestinationName()});
		}
		unitService.setFree();
	}
}
