package com.cts.pdfstamper.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PDFStamperUtil {
	
	private final static int FONTSIZE=30;
	private final static int XCOORD_DELTA=+50;
	private final static int YCOORD_DELTA=-30;
	private final static float OPACITY=1f;
	private final static int ZERO=0;
	
	public static byte[] stampPDF(String pdfFilePath, String sequenceNo) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfReader pdfReader = null;
		PdfStamper pdfStamper = null;
		try {
			pdfReader = new PdfReader(pdfFilePath);
			int noOfPages = pdfReader.getNumberOfPages();
			pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
			Font font = new Font(FONTSIZE);
			Phrase phrase = new Phrase(sequenceNo, font);
			PdfGState pdfGState = new PdfGState();
			pdfGState.setFillOpacity(OPACITY);
			PdfContentByte pdfContentByte;
			Rectangle pageSize;
			float xCoordinate, yCoordinate;
			for (int count = 1; count <= noOfPages; count++) {
				pageSize = pdfReader.getPageSizeWithRotation(count);
				xCoordinate = pageSize.getLeft()+XCOORD_DELTA;
				yCoordinate = pageSize.getTop() +YCOORD_DELTA;
				pdfContentByte = pdfStamper.getOverContent(count);
				pdfContentByte.saveState();
				pdfContentByte.setGState(pdfGState);
				ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_CENTER, phrase, xCoordinate, yCoordinate, ZERO);
				pdfContentByte.restoreState();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null!=pdfStamper)
					pdfStamper.close();
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(null!=pdfReader)
				pdfReader.close();
		}
		return byteArrayOutputStream.toByteArray();
	}

	public static void main(String[] args) throws Exception{
		byte[] pdfBytes=stampPDF("C:\\Users\\Sabyasachi\\Downloads\\552449.pdf", "1000101");
		System.out.println("pdfBytes :"+pdfBytes);
		FileOutputStream fos = new FileOutputStream("C:\\Users\\Sabyasachi\\Downloads\\552449_Bytes.pdf");
		fos.write(pdfBytes);
		fos.close();
	}
}
