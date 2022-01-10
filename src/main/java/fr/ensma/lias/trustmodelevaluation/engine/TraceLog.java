package fr.ensma.lias.trustmodelevaluation.engine;

import fr.ensma.lias.trustmodelevaluation.model.IValueInt;

/**
 * @author Mickael BARON
 */
public class TraceLog {

	private String application;

	private IValueInt value;

	public TraceLog(String pApplication, final int pValue) {
		this.application = pApplication;

		value = new IValueInt() {
			@Override
			public int getValue() {
				return pValue;
			}
		};
	}

	public String getApplication() {
		return application;
	}

	public IValueInt getValue() {
		return value;
	}
}
