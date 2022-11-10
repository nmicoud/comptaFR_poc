package org.tgi.factories;

import org.tgi.process.XXA_FactGeneJournaux;

public class ComptaFrProcessFactory extends TgiProcessFactory {

	@Override
	protected void initialize() {
		registerProcess(XXA_FactGeneJournaux.class);
		
	}

}
