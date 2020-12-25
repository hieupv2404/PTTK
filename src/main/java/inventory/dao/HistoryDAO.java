package inventory.dao;

import inventory.model.History;

public interface HistoryDAO<E> extends BaseDAO<E> {
    void saveDTO(History history);
}
