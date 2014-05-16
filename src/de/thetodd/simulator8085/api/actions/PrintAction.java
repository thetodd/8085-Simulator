package de.thetodd.simulator8085.api.actions;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import de.thetodd.simulator8085.api.Simulator;

/**
 * Prints a listing of the written code. First you get the memoryaddress
 * followed by the opcodes, the you have the line of code syntaxhighlighted.
 * 
 * @author Florian Schleich <florian.schleich@gmail.com>
 * 
 */
public class PrintAction implements Action {

	private String code;
	private String filename;

	public PrintAction(String code, String filename) {
		this.code = code;
		this.filename = filename;
	}

	public void run() {
		if (Simulator.getInstance().isAssembled()) {
			ArrayList<Line> codeLines = new ArrayList<>();
			String[] lines = code.split("\r\n");
			int i = 0;
			for (String string : lines) {
				if (!string.toUpperCase().startsWith("ORG")
						&& !string.toUpperCase().startsWith("@")) {
					Line line = new Line();
					for (Entry<Short, Integer> e : Simulator.getInstance()
							.getCodeMap().entrySet()) {
						if (e.getValue() == i) {
							line.address = e.getKey();
							break;
						}
					}
					byte[] opc = Simulator.getInstance().getOpcodes(string);
					line.opcodes = opc;
					line.codeLine = string;
					codeLines.add(line);

				}
				i++;
			}

			PDDocument document;
			try {
				document = new PDDocument();
				PDPage page = new PDPage();
				document.addPage(page);

				PDFont header = PDType1Font.HELVETICA;
				PDFont codeFont = PDType1Font.COURIER;
				PDFont footer = PDType1Font.HELVETICA_BOLD;

				PDPageContentStream contentStream = new PDPageContentStream(
						document, page);

				contentStream.beginText();
				contentStream.setFont(header, 12);
				contentStream.moveTextPositionByAmount(30, 750);
				contentStream.drawString(filename);
				contentStream.endText();

				int n = 730;
				contentStream.setFont(codeFont, 10);
				for (Line l : codeLines) {
					if (l.opcodes.length > 0) {
						contentStream.beginText();
						contentStream.setNonStrokingColor(255, 100, 100);
						contentStream.moveTextPositionByAmount(30, n);
						contentStream.drawString(String.format("%04X:",
								l.address));
						contentStream.endText();

						contentStream.beginText();
						contentStream.setNonStrokingColor(100, 100, 255);
						contentStream.moveTextPositionByAmount(70, n);
						contentStream.drawString(l.getOpcodes());
						contentStream.endText();
					}

					contentStream.beginText();
					contentStream.setNonStrokingColor(0, 0, 0);
					if (l.codeLine.startsWith(";")) {
						contentStream.setNonStrokingColor(80, 160, 80);
					}
					contentStream.moveTextPositionByAmount(150, n);
					contentStream.drawString(l.codeLine);
					contentStream.endText();

					n -= 15;
				}

				contentStream.beginText();
				contentStream.moveTextPositionByAmount(30, 30);
				contentStream.setFont(footer, 8);
				contentStream.setNonStrokingColor(180, 180, 180);
				String date = DateFormat.getDateInstance(DateFormat.LONG)
						.format(new Date());
				contentStream.drawString("Created with 8085-Simulator. Date: "
						+ date);
				contentStream.endText();

				// Make sure that the content stream is closed:
				contentStream.close();

				// Save the results and ensure that the document is properly
				// closed:
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"PDF Files", "pdf");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getAbsolutePath();
					if (!path.endsWith(".pdf")) {
						path += ".pdf";
					}
					document.save(path);
				}
				document.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (COSVisitorException e) {
				e.printStackTrace();
			}
		}
	}

	class Line {
		public short address;
		public byte[] opcodes;
		public String codeLine;

		public String getOpcodes() {
			String op = "";
			if (opcodes.length > 0) {
				for (byte b : opcodes) {
					op += String.format("%02X ", b);
				}
			}
			return op;
		}

		public String toString() {
			return address + ": " + codeLine;
		}
	}

}
