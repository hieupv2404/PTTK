package inventory.dao;

import inventory.model.Issue;

import java.sql.SQLException;

public interface IssueDAO<E> extends BaseDAO<E> {
    void saveDTO(Issue issue) throws SQLException;
    void updateDTO(Issue issue) throws SQLException;
    Issue findByIdDTO(int id);
}
