package inventory.dao;

import inventory.model.IssueDetail;

import java.sql.SQLException;

public interface IssueDetailDAO<E> extends BaseDAO<E> {
    void saveDTO(IssueDetail issueDetail) throws SQLException;
    void updateDTO(IssueDetail issueDetail) throws SQLException;
    IssueDetail findByIdDTO(int id);
}
