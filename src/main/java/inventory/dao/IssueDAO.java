package inventory.dao;

import inventory.model.Issue;

public interface IssueDAO<E> extends BaseDAO<E> {
    void saveDTO(Issue issue);
    void updateDTO(Issue issue);
    Issue findByIdDTO(int id);
    void deleteDTO(Issue issue);
}
