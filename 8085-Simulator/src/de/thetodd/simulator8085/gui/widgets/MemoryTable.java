package de.thetodd.simulator8085.gui.widgets;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.listener.ProcessorChangedListener;
import de.thetodd.simulator8085.api.listener.RegisterChangeEvent;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.gui.Messages;

public class MemoryTable extends TableViewer implements ProcessorChangedListener {

	public MemoryTable(Composite parent) {
		super(parent, SWT.BORDER
				| SWT.FULL_SELECTION);
		getTable().setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
		getTable().setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tblclmnAdresse = tableViewerColumn.getColumn();
		tblclmnAdresse.setWidth(64);
		tblclmnAdresse
				.setText(Messages.SimulatorMainWindow_tblclmnAdresse_text);

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn_1.getColumn();
		tableColumn.setWidth(30);
		tableColumn.setText("0");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tableColumn_1 = tableViewerColumn_2.getColumn();
		tableColumn_1.setWidth(30);
		tableColumn_1.setText("1");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tableColumn_2 = tableViewerColumn_3.getColumn();
		tableColumn_2.setWidth(30);
		tableColumn_2.setText("2");

		TableViewerColumn tableViewerColumn_16 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tableColumn_9 = tableViewerColumn_16.getColumn();
		tableColumn_9.setWidth(30);
		tableColumn_9.setText("3");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tableColumn_3 = tableViewerColumn_4.getColumn();
		tableColumn_3.setWidth(30);
		tableColumn_3.setText("4");

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tableColumn_4 = tableViewerColumn_5.getColumn();
		tableColumn_4.setWidth(30);
		tableColumn_4.setText("5");

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tableColumn_5 = tableViewerColumn_6.getColumn();
		tableColumn_5.setWidth(30);
		tableColumn_5.setText("6");

		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tableColumn_6 = tableViewerColumn_7.getColumn();
		tableColumn_6.setWidth(30);
		tableColumn_6.setText("7");

		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tableColumn_7 = tableViewerColumn_8.getColumn();
		tableColumn_7.setWidth(30);
		tableColumn_7.setText("8");

		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tableColumn_8 = tableViewerColumn_9.getColumn();
		tableColumn_8.setWidth(30);
		tableColumn_8.setText("9");

		TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tblclmnA = tableViewerColumn_10.getColumn();
		tblclmnA.setWidth(30);
		tblclmnA.setText("A");

		TableViewerColumn tableViewerColumn_11 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tblclmnB = tableViewerColumn_11.getColumn();
		tblclmnB.setWidth(30);
		tblclmnB.setText("B");

		TableViewerColumn tableViewerColumn_12 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tblclmnC = tableViewerColumn_12.getColumn();
		tblclmnC.setWidth(30);
		tblclmnC.setText("C");

		TableViewerColumn tableViewerColumn_13 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tblclmnD = tableViewerColumn_13.getColumn();
		tblclmnD.setWidth(30);
		tblclmnD.setText("D");

		TableViewerColumn tableViewerColumn_14 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tblclmnE = tableViewerColumn_14.getColumn();
		tblclmnE.setWidth(30);
		tblclmnE.setText("E");

		TableViewerColumn tableViewerColumn_15 = new TableViewerColumn(
				this, SWT.NONE);
		TableColumn tblclmnF = tableViewerColumn_15.getColumn();
		tblclmnF.setWidth(30);
		tblclmnF.setText("F");
		
		Simulator.getInstance().registerChangeListener(this);
	}

	@Override
	public void memoryChanged() {
		getTable().removeAll();
		for (int i = Memory.getInstance().getMemoryStart(); i <= Memory
				.getInstance().getMemoryEnd(); i += 16) {
			TableItem tableItem = new TableItem(getTable(), SWT.NONE);
			String[] mem = new String[17];
			mem[0] = String.format("0x%04X", i);
			for (int j = 0; j < 16; j++) {

				mem[1 + j] = String.format("%02x",
						Memory.getInstance().get((short) (i + j)));
			}
			tableItem.setText(mem);
		}
	}

	@Override
	public void registerChanged(RegisterChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outChanged(byte adr, byte value) {
		// TODO Auto-generated method stub
		
	}

}
