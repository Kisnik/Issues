package manager;

import issues.Issue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import repository.IssueRepository;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IssueManagerTest {

    private Issue[] issues;
    private IssueRepository issueRepository = new IssueRepository();
    private IssueManager issueManager = new IssueManager(issueRepository);

    //поиск открытых заявок
    @Nested
    public class FindOpenedIssues {

        @Test
        void findAllOpenedIfExist() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", true, labels, "Sidor");
            issues[2] = new Issue(3, "Iv", false, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.findAllOpened();
            List<Issue> expected = new ArrayList<>();
            expected.add(issues[0]);
            expected.add(issues[2]);
            assertIterableEquals(actual, expected);
        }

        @Test
        void findOneOpenedIfExist() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", true, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", true, labels, "Sidor");
            issues[2] = new Issue(3, "Iv", false, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.findAllOpened();
            List<Issue> expected = new ArrayList<>();
            expected.add(issues[2]);
            assertIterableEquals(actual, expected);
        }

        @Test
        void findAllOpenedIfNotExist() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", true, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", true, labels, "Sidor");
            issues[2] = new Issue(3, "Iv", true, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.findAllOpened();
            List<Issue> expected = new ArrayList<>();
            assertIterableEquals(actual, expected);
        }
    }

    //поиск закрытых заявок
    @Nested
    public class FindClosedIssues {

        @Test
        void findAllClosedIfExist() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", true, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labels, "Sidor");
            issues[2] = new Issue(3, "Iv", true, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.findAllClosed();
            List<Issue> expected = new ArrayList<>();
            expected.add(issues[0]);
            expected.add(issues[2]);
            assertIterableEquals(actual, expected);
        }

        @Test
        void findOneClosedIfExist() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labels, "Sidor");
            issues[2] = new Issue(3, "Iv", true, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.findAllClosed();
            List<Issue> expected = new ArrayList<>();
            expected.add(issues[2]);
            assertIterableEquals(actual, expected);
        }

        @Test
        void findAllClosedIfNotExist() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labels, "Sidor");
            issues[2] = new Issue(3, "Iv", false, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.findAllClosed();
            List<Issue> expected = new ArrayList<>();
            assertIterableEquals(actual, expected);
        }
    }

    //поиск по автору
    @Nested
    public class FilerByAuthor {

        //ни одного совпадения
        @Test
        void filterByAuthorNoMatches() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labels, "Sidor");
            issues[2] = new Issue(3, "Iv", false, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.filterBy(issueRepository.findAll(), issueManager.filterByAuthor("Tesr"));
            List<Issue> expected = new ArrayList<>();
            assertIterableEquals(actual, expected);

        }

        //одно совпадение
        @Test
        void filterByAuthorOneMatches() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labels, "Sidor");
            issues[2] = new Issue(3, "Iv", false, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.filterBy(issueRepository.findAll(), issueManager.filterByAuthor("Ivan"));
            List<Issue> expected = new ArrayList<>();
            expected.add(issues[1]);
            assertIterableEquals(actual, expected);

        }

        @Test
        void filterByAuthorSeveralMatches() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labels, "Sidor");
            issues[2] = new Issue(3, "Ivan", false, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.filterBy(issueRepository.findAll(), issueManager.filterByAuthor("Ivan"));
            List<Issue> expected = new ArrayList<>();
            expected.add(issues[1]);
            expected.add(issues[2]);
            assertIterableEquals(actual, expected);

        }

    }

    //поиск по назначению
    @Nested
    public class filterByAssignee {

        //ни одного совпадения
        @Test
        void filterByAssigneeNoMatches() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labels, "Sidor");
            issues[2] = new Issue(3, "Iv", false, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.filterBy(issueRepository.findAll(), issueManager.filterByAssignee("Tesr"));
            List<Issue> expected = new ArrayList<>();
            assertIterableEquals(actual, expected);

        }

        //одно совпадение
        @Test
        void filterByAssigneeOneMatches() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labels, "Sidor");
            issues[2] = new Issue(3, "Iv", false, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.filterBy(issueRepository.findAll(), issueManager.filterByAssignee("Sidor"));
            List<Issue> expected = new ArrayList<>();
            expected.add(issues[1]);
            assertIterableEquals(actual, expected);
        }

        @Test
        void filterByAssigneeSeveralMatches() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidor");
            issues[1] = new Issue(1, "Ivan", false, labels, "Sidor");
            issues[2] = new Issue(3, "Ivan", false, labels, "Sidorov");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.filterBy(issueRepository.findAll(), issueManager.filterByAssignee("Sidor"));
            List<Issue> expected = new ArrayList<>();
            expected.add(issues[0]);
            expected.add(issues[1]);
            assertIterableEquals(actual, expected);

        }

    }

    //посик по тегам
    @Nested
    class FilterByLabel {
        @Test
        void filterByLabelNoMatches() {
            HashSet<String> labelsOne = new HashSet<>();
            labelsOne.add("tag1");
            labelsOne.add("tag2");
            HashSet<String> labelsTwo = new HashSet<>();
            labelsTwo.add("tag1");
            labelsTwo.add("tag21");
            HashSet<String> labelsThree = new HashSet<>();
            labelsThree.add("test");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labelsOne, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labelsTwo, "Sidor");
            issues[2] = new Issue(3, "Iv", false, labelsTwo, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.filterBy(issueRepository.findAll(), issueManager.filterByLabel(labelsThree));
            List<Issue> expected = new ArrayList<>();
            assertIterableEquals(actual, expected);
        }

        @Test
        void filterByLabelOneMatch() {
            HashSet<String> labelsOne = new HashSet<>();
            labelsOne.add("tag1");
            labelsOne.add("tag2");
            HashSet<String> labelsTwo = new HashSet<>();
            labelsTwo.add("tag1");
            labelsTwo.add("tag21");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labelsOne, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labelsTwo, "Sidor");
            issues[2] = new Issue(3, "Iv", false, labelsTwo, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            List<Issue> actual = issueManager.filterBy(issueRepository.findAll(), issueManager.filterByLabel(labelsOne));
            List<Issue> expected = new ArrayList<>();
            expected.add(issues[0]);
            assertIterableEquals(actual, expected);
        }

        @Test
        void filterByLabelSeveralMatches() {
            HashSet<String> labelsOne = new HashSet<>();
            labelsOne.add("tag1");
            labelsOne.add("tag2");
            labelsOne.add("tag3");
            HashSet<String> labelsTwo = new HashSet<>();
            labelsTwo.add("tag1");
            labelsTwo.add("tag21");
            labelsTwo.add("tag3");
            HashSet<String> labelsThree = new HashSet<>();
            labelsTwo.add("tag13");
            labelsTwo.add("tag12");
            labelsTwo.add("tag3");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labelsOne, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labelsTwo, "Sidor");
            issues[2] = new Issue(3, "Iv", false, labelsThree, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[1]);
            issueRepository.save(issues[2]);
            HashSet<String> tmp = new HashSet<>();
            tmp.add("tag1");
            tmp.add("tag3");
            List<Issue> actual = issueManager.filterBy(issueRepository.findAll(), issueManager.filterByLabel(tmp));
            List<Issue> expected = new ArrayList<>();
            expected.add(issues[0]);
            expected.add(issues[1]);
            assertIterableEquals(actual, expected);
        }


    }

    //сортировка заявок
    @Nested
    class SortIssues {
        @Test
        void sortIssues() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[3];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidorov");
            issues[1] = new Issue(1, "Ivan", false, labels, "Sidor");
            issues[2] = new Issue(3, "Iv", false, labels, "Sid");
            issueRepository.save(issues[0]);
            issueRepository.save(issues[2]);
            issueRepository.save(issues[1]);
            List<Issue> actual = issueManager.sortIssues();
            List<Issue> expected = new ArrayList<>();
            expected.add(issues[1]);
            expected.add(issues[0]);
            expected.add(issues[2]);
            for (int i = 0; i < 3; i++) {
                assertEquals(actual.get(i).getId(), expected.get(i).getId());
            }
        }

    }

    @Nested
    class CloseIssue {

        //закрыть открытую заявку
        @Test
        void closeOpenedIssue() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[1];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidorov");
            issueRepository.save(issues[0]);
            issueManager.closeIssue(issues[0]);
            boolean expected = true;
            boolean actual = issues[0].isClosed();
            assertEquals(actual, expected);

        }

        //закрыть закрытую заявку
        @Test
        void closeClosedIssue() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[1];
            issues[0] = new Issue(2, "Ivanov", true, labels, "Sidorov");
            issueRepository.save(issues[0]);
            issueManager.closeIssue(issues[0]);
            boolean expected = true;
            boolean actual = issues[0].isClosed();
            assertEquals(actual, expected);

        }

    }

    @Nested
    class OpenIssue {

        //открыть открытую заявку
        @Test
        void closeOpenedIssue() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[1];
            issues[0] = new Issue(2, "Ivanov", false, labels, "Sidorov");
            issueRepository.save(issues[0]);
            issueManager.openIssue(issues[0]);
            boolean expected = false;
            boolean actual = issues[0].isClosed();
            assertEquals(actual, expected);

        }

        //открыть закрытую заявку
        @Test
        void closeClosedIssue() {
            HashSet<String> labels = new HashSet<>();
            labels.add("tag1");
            issues = new Issue[1];
            issues[0] = new Issue(2, "Ivanov", true, labels, "Sidorov");
            issueRepository.save(issues[0]);
            issueManager.openIssue(issues[0]);
            boolean expected = false;
            boolean actual = issues[0].isClosed();
            assertEquals(actual, expected);

        }
    }
}