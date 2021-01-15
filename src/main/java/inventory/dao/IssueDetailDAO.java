package inventory.dao;

import inventory.model.IssueDetail;

public interface IssueDetailDAO<E> extends BaseDAO<E> {
    void saveDTO(IssueDetail issueDetail);
    void updateDTO(IssueDetail issueDetail);
    IssueDetail findByIdDTO(int id);
}
