package inventory.dao;

import inventory.model.Vat;
import inventory.model.VatDetail;

public interface VatDetailDAO<E> extends BaseDAO<E> {
    void saveDTO(VatDetail vatDetail);
    void updateDTO(VatDetail vatDetail);
    VatDetail findByIdDTO(int id);
}
