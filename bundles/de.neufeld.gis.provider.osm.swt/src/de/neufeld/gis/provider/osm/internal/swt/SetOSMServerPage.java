package de.neufeld.gis.provider.osm.internal.swt;

import java.io.Serializable;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.neufeld.gis.ui.CreateMapWizardPage;

public class SetOSMServerPage extends CreateMapWizardPage {

	private String osmServer;

	public SetOSMServerPage() {
		super("SetOSMServerPage");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite=new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		
		new Label(composite, SWT.NONE).setText("OSM Server"+":");
		final Text text = new Text(composite, SWT.BORDER);
		text.setMessage("Enter OSM Server. Example: http://tile.openstreetmap.org");
		text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				osmServer=text.getText();
				setPageComplete(true);
			}
		});
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(text);
		

		setControl(composite);
		setPageComplete(false);
	}

	@Override
	public Serializable getMapProviderSpecifcData() {
		return osmServer;
	}

}
