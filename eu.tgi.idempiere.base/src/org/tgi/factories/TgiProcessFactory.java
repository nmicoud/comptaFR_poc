package org.tgi.factories;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.IProcessFactory;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessCall;
import org.compiere.util.CLogger;
import org.tgi.process.ComptaFrProcess;

/**
 * Tgi Process dynamic factory
 * Inspired by ingeint / idempiere-plugin-scaffold - src/com/ingeint/template/base/CustomProcessFactory.java
 */

public abstract class TgiProcessFactory implements IProcessFactory {

	private final static CLogger log = CLogger.getCLogger(TgiProcessFactory.class);
	private List<Class<? extends ComptaFrProcess>> cacheProcess = new ArrayList<Class<? extends ComptaFrProcess>>();
	
	/**
	 * For initialize class. Register the process to build
	 * 
	 * <pre>
	 * protected void initialize() {
	 * 	registerProcess(PPrintPluginInfo.class);
	 * }
	 * </pre>
	 */
	protected abstract void initialize();

	/**
	 * Register process
	 * 
	 * @param processClass Process class to register
	 */
	protected void registerProcess(Class<? extends ComptaFrProcess> processClass) {
		cacheProcess.add(processClass);
		log.info(String.format("ExpertLightProcess registered -> %s", processClass.getName()));
	}

	/**
	 * Default constructor
	 */
	public TgiProcessFactory() {
		initialize();
	}

	@Override
	public ProcessCall newProcessInstance(String className) {
		for (int i = 0; i < cacheProcess.size(); i++) {
			if (className.equals(cacheProcess.get(i).getName())) {
				try {
					ComptaFrProcess customProcess = cacheProcess.get(i).getConstructor().newInstance();
					log.info(String.format("ExpertLightProcess created -> %s", className));
					return customProcess;
				} catch (Exception e) {
					log.severe(String.format("Class %s can not be instantiated, Exception: %s", className, e));
					throw new AdempiereException(e);
				}
			}
		}
		return null;
	}
}
