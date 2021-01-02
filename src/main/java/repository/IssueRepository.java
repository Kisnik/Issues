package repository;

import issues.Issue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IssueRepository {

    private List<Issue> issues = new ArrayList<>();


    public void save(Issue issue) {
        this.issues.add(issue);
    }

    public void delete(int id) {

    }

    public List<Issue> findAll() {
        return issues;
    }


}
