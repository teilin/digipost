package net.teilin.hackathon24.lib;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;


import android.util.Log;

public class Pdf2Text {

	PDDocument pddDocument;
	PDFTextStripper textStripper;
	
	public Pdf2Text(String filePath) {
		try {
			this.pddDocument = PDDocument.load(new File(filePath));
			this.textStripper = new PDFTextStripper();
		}catch(Exception e){
			Log.e("P2T", e.getMessage());
		}
	}
	
	public String getText() {
		String out = null;
		try {
			out = this.textStripper.getText(this.pddDocument);
		} catch(Exception e) {
			Log.e("Read pdf",e.getStackTrace().toString());
			out = "Can't read the pdf file";
		}
		return out;
	}
}
