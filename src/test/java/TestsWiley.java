import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.EducationPage;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.StudentsPage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class TestsWiley {

    private static WebDriver driver;
    private static HomePage homePage;
    private static StudentsPage studentsPage;
    private static EducationPage educationPage;
    private static SearchResultsPage searchResultsPage;

    @BeforeClass
    public static void beforeClass() {
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        homePage = PageFactory.initElements(driver, HomePage.class);
        studentsPage = PageFactory.initElements(driver, StudentsPage.class);
        educationPage = PageFactory.initElements(driver, EducationPage.class);
        searchResultsPage = PageFactory.initElements(driver, SearchResultsPage.class);

        driver.navigate().to("https://www.wiley.com/en-us");
        homePage.clickYesPopUpButton();
    }

    @Before
    public void start() {
        driver.navigate().to("https://www.wiley.com/en-us");
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }

    /**
     * Open https://www.wiley.com/en-us
     * Check the following links are displayed in the top menu
     * - Who We Serve
     * - Subjects
     * - About
     */
    @Test
    public void testDisplayedLinksTopMenu() {
        assertTrue("No such element - link \"Who we serve\"", isElementPresent(homePage.getWhoWeServe()));
        assertTrue("No such element - link \"Subject\"", isElementPresent(homePage.getSubject()));
        assertTrue("No such element - link \"About\"", isElementPresent(homePage.getAbout()));
    }


    /**
     * Check items under Who We Serve for sub-header
     * There are 11 items under resources sub-header
     * Titles are  “Students”, “Instructors”, “Book Authors”, “Professionals”, “Researchers”, “Institutions”,
     * “Librarians”, “Corporations”, “Societies”, “Journal Editors”,  “Government”
     */
    @Test
    public void checkItemsUnderWhoWeServe() {
        homePage.clickWhoWeServe();

        assertNotNull(homePage.getListItemsUnderWhoWeServe());
        List<String> listText = homePage.getListTextItemsUnderWhoWeServe();

        assertEquals(12, listText.size());
        assertTrue(listText.contains("Students"));
        assertTrue(listText.contains("Instructors"));
        assertTrue(listText.contains("Book Authors"));
        assertTrue(listText.contains("Professionals"));
        assertTrue(listText.contains("Researchers"));
        assertTrue(listText.contains("Institutions"));
        assertTrue(listText.contains("Librarians"));
        assertTrue(listText.contains("Corporations"));
        assertTrue(listText.contains("Societies"));
        assertTrue(listText.contains("Journal Editors"));
        assertTrue(listText.contains("Bookstores"));
        assertTrue(listText.contains("Government"));
    }

    /**
     * Click “Students” item
     * Check that https://www.wiley.com/en-us/students url is opened
     * Check that “Students” header is displayed
     * Check that “Learn More” links are present on the page and direct to  www.wileyplus.com site
     */
    @Test
    public void testStudentsPage() {
        homePage.clickWhoWeServe();
        homePage.clickStudents();

        assertEquals("https://www.wiley.com/en-us/students", driver.getCurrentUrl());
        assertEquals("Students", studentsPage.getHeader().getText());

        for (String domainName : studentsPage.getDomainNamesFromLearnMoreList()) {
            assertEquals("www.wileyplus.com", domainName);
        }
    }

    /**
     * Go to “Subjects” top menu, select “Education”
     * Check “Education” header is displayed
     * 13 items are displayed under “Subjects” on the left side of the screen and the texts are
     * "Information & Library Science",
     * "Education & Public Policy",
     * "K-12 General",
     * "Higher Education General",
     * "Vocational Technology",
     * "Conflict Resolution & Mediation (School settings)",
     * "Curriculum Tools- General",
     * "Special Educational Needs",
     * "Theory of Education",
     * "Education Special Topics",
     * "Educational Research & Statistics",
     * "Literacy & Reading",
     * "Classroom Management"
     */
    @Test
    public void testEducationPage() {
        homePage.moveToSubjects();
        homePage.clickEducation();

        assertEquals("Education", educationPage.getHeader().getText());
        List<String> linkList = educationPage.getTextSubjectLinkList();

        assertEquals(13, linkList.size());

        assertTrue(linkList.contains("Information & Library Science"));
        assertTrue(linkList.contains("Education & Public Policy"));
        assertTrue(linkList.contains("K-12 General"));
        assertTrue(linkList.contains("Higher Education General"));
        assertTrue(linkList.contains("Vocational Technology"));
        assertTrue(linkList.contains("Conflict Resolution & Mediation (School settings)"));
        assertTrue(linkList.contains("Curriculum Tools- General"));
        assertTrue(linkList.contains("Special Educational Needs"));
        assertTrue(linkList.contains("Theory of Education"));
        assertTrue(linkList.contains("Education Special Topics"));
        assertTrue(linkList.contains("Educational Research & Statistics"));
        assertTrue(linkList.contains("Literacy & Reading"));
        assertTrue(linkList.contains("Classroom Management"));
    }

    /**
     * Click on the Wiley logo at the top menu (left side of the top menu)
     * Home page is opened
     */
    @Test
    public void testClickLogo() {
        homePage.clickLogo();
        assertEquals("https://www.wiley.com/en-us", driver.getCurrentUrl());
    }

    /**
     * Do not enter anything in the search input and press search button
     * Nothing happens, home page is still displayed
     */
    @Test
    public void testEmptyInputSearch() {
        String pageSource = driver.getPageSource();

        homePage.clearSearchInput();
        homePage.clickSearchButton();

        String nextPageSource = driver.getPageSource();
        assertEquals(pageSource, nextPageSource);

        assertEquals("https://www.wiley.com/en-us", driver.getCurrentUrl());
    }

    /**
     * Enter “Java” and do not press search button
     * Area with related content is displayed right under the search header
     * On the “Suggestions” section, it has 4 words starting with “Java”
     * On the “Products” section, there are 5 titles and each title contain “Java” word
     */
    @Test
    public void testAreaWithRelatedSearchContent() {
        homePage.clearSearchInput();
        homePage.sendKeysSearchInput("java");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(homePage.getSearchArea()));

        WebElement searchArea = homePage.getSearchArea();
        assertTrue(searchArea.isDisplayed());
        assertEquals("-23px", searchArea.getCssValue("top"));
        assertEquals("0px", searchArea.getCssValue("left"));
        assertEquals("relative", searchArea.getCssValue("position"));

        List<String> searchSectionSuggestions = homePage.getTextListSearchSectionSuggestions();

        assertEquals(4, searchSectionSuggestions.size());
        searchSectionSuggestions.forEach(s -> assertTrue(s.startsWith("java")));

        List<String> textListSearchSectionProducts = homePage.getTextListSearchSectionProducts();

        assertEquals(4, textListSearchSectionProducts.size());
        textListSearchSectionProducts.forEach(s -> assertTrue(s.contains("Java")));

        homePage.clearSearchInput();
    }

    /**
     * Click “SEARCH” button
     * Only titles containing “Java” are displayed
     * There are 10 titles
     * Each title has at least one “Add to Cart” button
     */
    @Test
    public void testJavaSearchResults() {
        homePage.clearSearchInput();
        homePage.sendKeysSearchInput("java");
        homePage.clickSearchButton();

        List<String> listTextResultsTitles = searchResultsPage.getListTextResultsTitles();
        assertEquals(10, listTextResultsTitles.size());
        listTextResultsTitles.forEach(s -> assertTrue(s.contains("Java")));

        searchResultsPage.getListCountsResultWithButtonAddToCart().forEach(count ->
                assertTrue("Not all items contain a button 'ADD TO CART'", count > 0));
    }

    /**
     * Enter “Java” in the search input at the top and press “SEARCH” button
     * Make sure there are same 10 titles shown (as in step 8)
     */
    @Test
    public void testJavaSearchResultsAgain() {
        homePage.clearSearchInput();
        homePage.sendKeysSearchInput("java");
        homePage.clickSearchButton();

        List<String> prevListTextResultsTitles = searchResultsPage.getListTextResultsTitles();

        searchResultsPage.clearInputSearch();
        searchResultsPage.sendKeysSearchInput("java");

        List<String> nextListTextResultsTitles = searchResultsPage.getListTextResultsTitles();

        assertEquals(prevListTextResultsTitles, nextListTextResultsTitles);
    }

    private static boolean isElementPresent(WebElement element) {
        try {
            element.getText();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


}
