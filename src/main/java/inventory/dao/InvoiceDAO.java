package inventory.dao;

import inventory.model.Invoice;

public interface InvoiceDAO<E> extends BaseDAO<E> {
    void saveDTO(Invoice invoice);
    void updateDTO(Invoice invoice);

}
