package inventory.dao;


import inventory.model.InvoiceTemp;

public interface InvoiceTempDAO<E> extends BaseDAO<E> {
    void saveDTO(InvoiceTemp invoiceTemp);
    void updateDTO(InvoiceTemp invoiceTemp);
    InvoiceTemp findById(int id);
}
