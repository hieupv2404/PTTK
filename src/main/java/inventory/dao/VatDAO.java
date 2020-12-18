package inventory.dao;

import inventory.model.Vat;

public interface VatDAO<E> extends BaseDAO<E> {
    public void saveDTO(Vat vat);
    public void updateDTO(Vat vat);
    public Vat findByIdDTO(int id);
}
