package manager;

import issues.Issue;
import repository.IssueRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IssueManager implements Predicate<Issue> {

    private IssueRepository issueRepository;

    public IssueManager(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    //поиск открытых заявок
    public List<Issue> findAllOpened() {
        List<Issue> tmp = new ArrayList<>();
        for (Issue issue : issueRepository.findAll()) {
            if (!issue.isClosed()) {
                tmp.add(issue);
            }
        }

        return tmp;

    }

    //поиск закрытых заявок
    public List<Issue> findAllClosed() {
        List<Issue> tmp = new ArrayList<>();
        for (Issue issue : issueRepository.findAll()) {
            if (issue.isClosed()) {
                tmp.add(issue);
            }
        }

        return tmp;

    }


    public Predicate<Issue> filterByAuthor(String author) {
        return p -> author.equalsIgnoreCase(p.getAuthor());
    }

    public Predicate<Issue> filterByAssignee(String assignee) {
        return p -> p.getAssignee().equalsIgnoreCase(assignee);
    }

    public Predicate<Issue> filterByLabel(HashSet<String> label) {
        return p -> p.getLabel().containsAll(label);
    }

    //фильтрация по автору
    public List<Issue> filterBy(List<Issue> issues, Predicate<Issue> predicate) {

        return issues.stream().filter(predicate).collect(Collectors.<Issue>toList());
    }

    //сортировка Issues по id
    public List<Issue> sortIssues() {
        Collections.sort(issueRepository.findAll());
        return issueRepository.findAll();
    }

    //закрыть Issue
    public void closeIssue(Issue issue) {
        issue.setClosed(true);
    }

    //открыть Issue
    public void openIssue(Issue issue) {
        issue.setClosed(false);
    }

    @Override
    public boolean test(Issue issue) {
        return false;
    }

    @Override
    public Predicate<Issue> and(Predicate<? super Issue> other) {
        return null;
    }

    @Override
    public Predicate<Issue> negate() {
        return null;
    }

    @Override
    public Predicate<Issue> or(Predicate<? super Issue> other) {
        return null;
    }
}
