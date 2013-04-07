package de.neufeld.gis.ui;

import java.io.Serializable;

import org.eclipse.jface.wizard.WizardPage;

public abstract class CreateMapWizardPage extends WizardPage {

	protected CreateMapWizardPage(String pageName) {
		super(pageName);
	}

	public abstract Serializable getMapProviderSpecifcData();

}
