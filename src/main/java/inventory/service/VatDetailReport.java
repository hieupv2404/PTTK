package inventory.service;

import inventory.model.InvoiceTemp;
import inventory.model.VatDetailTemp;
import inventory.util.Constant;
import inventory.util.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class VatDetailReport extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String fileName = "vat-detail-report"+System.currentTimeMillis()+".xlsx";
		response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\"");


		Sheet sheet = workbook.createSheet("data");
		Row title = sheet.createRow(0);
//			HSSFCell cell = (HSSFCell) row.createCell(rownum, CellType.FORMULA);
//		// Sét công thức.
//			cell.setCellFormula("SUM(D2:D4)");
		title.createCell(1).setCellValue(" Vat Detail");

		Row header = sheet.createRow(1);
		header.createCell(0).setCellValue("#");
		header.createCell(1).setCellValue("Product");
		header.createCell(2).setCellValue("Qty");
		header.createCell(3).setCellValue("Price");
		header.createCell(4).setCellValue("Price Total");
		header.createCell(5).setCellValue("Vat Code");
		header.createCell(6).setCellValue("Supplier");

		List<VatDetailTemp> invoiceTempList =(List<VatDetailTemp>) model.get(Constant.KEY_GOODS_RECEIPT_REPORT);
//		List<Invoice> invoices =(List<Invoice>) model.get(Constant.KEY_GOODS_RECEIPT_REPORT);

		int rownum=2;
		for(VatDetailTemp invoiceTemp :invoiceTempList) {
			Row row = sheet.createRow(rownum++);
			row.createCell(0).setCellValue(rownum-2);
			row.createCell(1).setCellValue(invoiceTemp.getProductName());
			row.createCell(2).setCellValue(invoiceTemp.getQty());
			row.createCell(3).setCellValue(Float.parseFloat(invoiceTemp.getPriceOne().toString()));
			row.createCell(4).setCellValue(Float.parseFloat(invoiceTemp.getPriceTotal().toString()));
			row.createCell(5).setCellValue(invoiceTemp.getVatName());
			row.createCell(6).setCellValue(invoiceTemp.getSupplierName());
		}
//		for(Invoice invoice :invoices) {
//			Row row = sheet.createRow(rownum++);
//			row.createCell(0).setCellValue(rownum-1);
//			row.createCell(1).setCellValue(invoice.getCode());
//			row.createCell(2).setCellValue(invoice.getQty());
//			row.createCell(3).setCellValue(Float.parseFloat(invoice.getPrice().toString()));
//			row.createCell(4).setCellValue(invoice.getProductInfo().getName());
//			row.createCell(5).setCellValue(DateUtil.dateToString(invoice.getUpdateDate()));
//		}
			Row row = sheet.createRow(rownum++);
//			HSSFCell cell = (HSSFCell) row.createCell(rownum, CellType.FORMULA);
//		// Sét công thức.
//			cell.setCellFormula("SUM(D2:D4)");
			row.createCell(1).setCellValue("Total: ");
			row.createCell(2).setCellValue("=SUM(C3:C"+String.valueOf(rownum-1)+")");
			row.createCell(3).setCellValue("=SUM(D3:D"+String.valueOf(rownum-1)+")");
			row.createCell(4).setCellValue("=SUM(E3:E"+String.valueOf(rownum-1)+")");
	}

}
