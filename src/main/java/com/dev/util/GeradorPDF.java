package com.dev.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dev.dto.ClientDTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GeradorPDF {

	private static Logger logger = LoggerFactory.getLogger(GeradorPDF.class);
	
	private static Font font1 = FontFactory.getFont(FontFactory.COURIER_BOLDOBLIQUE, 6, BaseColor.BLACK);
	private static Font font8 = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
	private static Font font7 = FontFactory.getFont(FontFactory.HELVETICA, 7, BaseColor.BLACK);
	private static Font fontCFO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
	private static Font font10Bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK);

	public static PdfPTable criaCabecalho(String[] titulos, int numeroColunas) {

		PdfPTable table = new PdfPTable(numeroColunas);
		// Add PDF Table Header ->
		Stream.of(titulos).forEach(headerTitle -> {
			PdfPCell header = new PdfPCell();
			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setHorizontalAlignment(Element.ALIGN_CENTER);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(headerTitle, headFont));
			table.addCell(header);
		});
		return table;

	}

	public static PdfPTable criaLinhaTabela(List<Integer> obj, PdfPTable table) {

		for (int i = 0; i < obj.size(); i++) {

			PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(obj.get(i)), fontCFO));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

		}

		return table;
	}

	private static Integer sum(List<Integer> elements) {
		Integer soma = 0;
		for (Integer obj : elements) {
			soma += obj;
		}
		return soma;
	}


	public static ByteArrayInputStream customerPDFReport(List<ClientDTO> customers) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		document.setPageSize(PageSize.A4.rotate());

		try {

			PdfWriter.getInstance(document, out);
			document.open();

			Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
			
			
			Paragraph para = new Paragraph("Relatório Aleatório", font);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);

			List<Integer> numbers = new ArrayList<>();
			numbers.add(10);
			numbers.add(30);
			numbers.add(20);
			numbers.add(200);
			numbers.add(sum(numbers));

			List<Integer> numbers2 = new ArrayList<>();
			numbers2.add(0);
			numbers2.add(0);
			numbers2.add(sum(numbers2));

			List<Integer> numbers3 = new ArrayList<>();
			numbers3.add(1);
			numbers3.add(300);
			numbers3.add(1);
			numbers3.add(300);
			numbers3.add(1);
			numbers3.add(300);
			numbers3.add(1);
			numbers3.add(300);
			numbers3.add(sum(numbers));

			List<Integer> numbersX = new ArrayList<>();
			numbersX.add(1);
			numbersX.add(300);
			numbersX.add(sum(numbersX));

			List<List<Integer>> matrix = new ArrayList<>();
			matrix.add(numbers);
			matrix.add(numbers2);
			matrix.add(numbers3);
			matrix.add(numbersX);
			matrix.add(numbers);
			matrix.add(numbers2);
			matrix.add(numbers3);
			matrix.add(numbersX);

			for (int i = 0; i < matrix.size(); i++) {

				int numCol = matrix.get(i).size();

				Boolean exibirBloco = sum(matrix.get(i)) > 0 ? true : false;
				
				if (exibirBloco) {
					String[] especies = {"Espécie 1", "Espécie 2", "Espécie 3", "Espécie 4", "Espécie 5", "Espécie 6","Espécie 7", "Espécie 8"};
					PdfPTable titulo = criaCabecalho(new String[] { especies[i] }, 1);
					document.add(titulo);
				}
				
				

				switch (numCol) {

				case 5:
					if (exibirBloco) {
						PdfPTable table5 = null;
						table5 = criaCabecalho(new String[] { "A", "B", "C", "D", "Total" }, numCol);
						criaLinhaTabela(matrix.get(i), table5);
						document.add(table5);
						document.add(Chunk.NEWLINE);
					}
					
					break;
					
				case 3:
					if(exibirBloco) {
						PdfPTable table3 = null;
						table3 = criaCabecalho(new String[] { "A", "B", "Total" }, numCol);
						criaLinhaTabela(matrix.get(i), table3);
						document.add(table3);
						document.add(Chunk.NEWLINE);
					}
					break;
					
				case 9:
					if(exibirBloco) {
						PdfPTable table9 = null;
						table9 = criaCabecalho(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "Total" }, numCol);
						criaLinhaTabela(matrix.get(i), table9);
						document.add(table9);
						document.add(Chunk.NEWLINE);
						
					}
					break;

				default:
					break;
				}
				
				
				
			}

			document.close();
		} catch (DocumentException e) {
			logger.error(e.toString());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

}
