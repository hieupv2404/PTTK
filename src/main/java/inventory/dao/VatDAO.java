package inventory.dao;

import inventory.model.Vat;

public interface VatDAO<E> extends BaseDAO<E> {
    void saveDTO(Vat vat);
    void updateDTO(Vat vat);
    Vat findByIdDTO(int id);
    void deleteDTO(Vat vat);
}
