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


    public List<Issue> filterByAuthor(String author) {
        Predicate<Issue> isAuthor = p -> author.equalsIgnoreCase(p.getAuthor());
        return filterBy(isAuthor);
    }

    public List<Issue> filterByAssignee(String assignee) {
        Predicate<Issue> isAssignee = p -> assignee.equalsIgnoreCase(p.getAssignee());
        return filterBy(isAssignee);
    }

    public List<Issue> filterByLabel(HashSet<String> label) {
        Predicate<Issue> isLabel = p -> p.getLabel().containsAll(label);
        return filterBy(isLabel);
    }

    public List<Issue> filterBy (Predicate<Issue> predicate) {
        List<Issue> issues = new ArrayList<>();
        for (Issue tmp:issueRepository.findAll()) {
            if(predicate.test(tmp)) {
                issues.add(tmp);
            }
        }
        return issues;
    }


    //сортировка Issues по id
    public List<Issue> sortIssues() {
        Collections.sort(issueRepository.findAll());
        return issueRepository.findAll();
    }

    //закрыть Issue
    public String closeIssue(int id) {
        for (Issue issue:issueRepository.findAll()) {
            if(issue.getId() == id) {
                issue.setClosed(true);
                return "Issue is closed";
            }
        }
        return "Issue doesn't exist";
    }

    //открыть Issue
    public String openIssue(int id) {
        for (Issue issue:issueRepository.findAll()) {
            if(issue.getId() == id) {
                issue.setClosed(false);
                return "Issue is opened";
            }
        }
        return "Issue doesn't exist";
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
