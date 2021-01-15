package inventory.service;

import inventory.model.Issue;

import java.util.Comparator;

public class UpdateDateCompatatorIssue implements Comparator<Issue> {

    @Override
    public int compare(Issue o1, Issue o2) {
        return o1.getUpdateDate().compareTo(o2.getUpdateDate());
    }
}
