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
import de.thetodd.simulator8085.api.listener.GlobalSimulatorEvents;
import de.thetodd.simulator8085.api.listener.ISimulatorListener;
import de.thetodd.simulator8085.api.listener.SimulatorEvent;

public class ListView extends Shell implements ISimulatorListener {
	private Table table;

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public ListView(Display display) {
		super(display, SWT.DIALOG_TRIM);
		createContents();

		Simulator.getInstance().registerSimulatorListener(this);
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
				Simulator.getInstance().unregisterSimulatorListener(
						ListView.this);
			}
		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void globalSimulatorEvent(SimulatorEvent evt) {
		if (evt.getEvent().equals(GlobalSimulatorEvents.PORT_WRITE)) {
			byte adr = Byte.parseByte(evt.getMessage().split(":")[0]);
			byte value = Byte.parseByte(evt.getMessage().split(":")[1]);
			TableItem[] items = table.getItems();
			boolean found = false;
			for (TableItem tableItem : items) {
				if (tableItem.getText(0).equals(String.format("%02xh", adr))) {
					tableItem.setText(1, String.format("%02xh", value));
					found = true;
					break;
				}
			}
			if (!found) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, String.format("%02xh", adr));
				item.setText(1, String.format("%02xh", value));
			}
		}
	}
}
