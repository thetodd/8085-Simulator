package de.thetodd.simulator8085.gui.outviews;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.listener.ProcessorChangedListener;
import de.thetodd.simulator8085.api.listener.RegisterChangeEvent;

public class ListView extends Shell implements ProcessorChangedListener {
	private Table table;

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public ListView(Display display) {
		super(display, SWT.DIALOG_TRIM);
		createContents();

		Simulator.getInstance().registerChangeListener(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		TableViewer tableViewer = new TableViewer(this, SWT.BORDER
				| SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnAddress = tableViewerColumn.getColumn();
		tblclmnAddress.setWidth(100);
		tblclmnAddress.setText("Address");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnValue = tableViewerColumn_1.getColumn();
		tblclmnValue.setWidth(100);
		tblclmnValue.setText("Value");
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("List Viewer");
		setSize(452, 302);


		addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				Simulator.getInstance().unregisterChangeListener(ListView.this);				
			}
		});
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void memoryChanged() {
	}

	@Override
	public void registerChanged(RegisterChangeEvent evt) {
	}

	@Override
	public void outChanged(byte adr, byte value) {
		TableItem[] items = table.getItems();
		boolean found = false;
		for (TableItem tableItem : items) {
			if(tableItem.getText(0).equals(String.format("%02xh", adr))) {
				tableItem.setText(1, String.format("%02xh", value));
				found = true;
				break;
			}
		}
		if(!found) {
			TableItem item = new TableItem(table,SWT.NONE);
			item.setText(0, String.format("%02xh", adr));
			item.setText(1, String.format("%02xh", value));
		}
	}
}
