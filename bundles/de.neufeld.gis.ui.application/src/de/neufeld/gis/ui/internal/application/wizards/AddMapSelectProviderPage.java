package de.neufeld.gis.ui.internal.application.wizards;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.neufeld.gis.core.MapDataProvider;
import de.neufeld.gis.core.MapDataProviderRegistry;

public class AddMapSelectProviderPage extends WizardPage {

	private MapDataProviderRegistry mapDataProviderRegistry;
	private String mapName;
	private MapDataProvider mapDataProvider;

	protected AddMapSelectProviderPage(MapDataProviderRegistry mapDataProviderRegistry) {
		super("AddMapSelectProvider");
		this.mapDataProviderRegistry = mapDataProviderRegistry;
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite=new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		
		new Label(composite, SWT.NONE).setText("Map Name"+":");
		final Text text = new Text(composite, SWT.BORDER);
		text.setMessage("Enter Map name");
		text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				mapName=text.getText();
				checkPageComplete();
			}
		});
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(text);
		
		new Label(composite, SWT.NONE).setText("Map Dataprovider"+":");
		ComboViewer providers = new ComboViewer(composite);
		providers.setContentProvider(ArrayContentProvider.getInstance());
		providers.setLabelProvider(new LabelProvider());
		providers.setInput(mapDataProviderRegistry.getProviders());
		providers.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				mapDataProvider=(MapDataProvider) ((IStructuredSelection)event.getSelection()).getFirstElement();
				checkPageComplete();
			}
		});
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(providers.getControl());
		
		setControl(composite);
		setPageComplete(false);
	}

	private void checkPageComplete() {
		setPageComplete(mapName!=null&&mapDataProvider!=null);
	}

	public String getMapName() {
		return mapName;
	}

	public MapDataProvider getMapDataProvider() {
		return mapDataProvider;
	}

}
