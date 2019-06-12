import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import pages.EducationPage;
import pages.HomePage;
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

    @BeforeClass
    public static void init() {
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        homePage = PageFactory.initElements(driver, HomePage.class);
        studentsPage = PageFactory.initElements(driver, StudentsPage.class);
        educationPage = PageFactory.initElements(driver, EducationPage.class);

        driver.navigate().to("https://www.wiley.com/en-us");
        homePage.clickYesPopUpButton();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }

    @Test
    public void checkDisplayedLinks() {
        assertTrue("No such element - link \"Who we serve\"", isElementPresent(homePage.getWhoWeServe()));
        assertTrue("No such element - link \"Subject\"", isElementPresent(homePage.getSubject()));
        assertTrue("No such element - link \"About\"", isElementPresent(homePage.getAbout()));
    }

    @Test
    public void checkItemsUnderWhoWeServe() {
        homePage.clickWhoWeServe();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    @Test
    public void testClickLogo(){
        homePage.clickLogo();
        assertEquals("https://www.wiley.com/en-us", driver.getCurrentUrl());
    }

    @Test
    public void testEmptyInputSearch(){
        String pageSource = driver.getPageSource();

        homePage.clearSearchInput();
        homePage.clickSearchButton();

        String nextPageSource = driver.getPageSource();
        assertEquals(pageSource, nextPageSource);

        assertEquals("https://www.wiley.com/en-us", driver.getCurrentUrl());
    }

    @Test
    public void testAreaWithRelatedSearchContent(){
        homePage.clearSearchInput();
        homePage.sendKeysSearchInput("java");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        textListSearchSectionProducts.forEach(s -> assertTrue(s.contains("java")));

        homePage.clearSearchInput();
    }

    //TODO убрать
    public static boolean isElementPresent(WebElement element) {
        try {
            element.getText();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


}
