package github;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;


public class SoftAssertionsSearch {

    @BeforeAll
    static void beforeAll() {
        Configuration.pageLoadStrategy = "eager";
        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void shouldFindJUnit5Code () {
        // открыть главную страницу
        open("https://github.com/");
        // ввести в поле поиска selenide и нажать enter
        $("[placeholder='Search GitHub']").setValue("selenide").pressEnter();
        // кликнуть на первый репозиторий из списка найденых
        $$("ul.repo-list li").first().$("a").click();
        // проверка: заголовок selenide/selenide
        $("#repository-container-header").shouldHave(text("selenide / selenide"));
        // перейти в раздел Wiki проекта
        $ ("#wiki-tab").click();
        // убедиться, что в списке страниц (Pages) есть страница SoftAssertions
        $("#wiki-pages-filter").setValue("Soft");
        $("[data-filterable-for=wiki-pages-filter]").shouldHave(text("SoftAssertions"));
        // открыть страницу SoftAssertions
        $ ("a[href='/selenide/selenide/wiki/SoftAssertions']").click();
        // проверить, что внутри есть пример кода для JUnit5
        $("#wiki-body").shouldHave(text("""
                @ExtendWith({SoftAssertsExtension.class})
                class Tests {
                  @Test
                  void test() {
                    Configuration.assertionMode = SOFT;
                    open("page.html");

                    $("#first").should(visible).click();
                    $("#second").should(visible).click();
                  }
                }"""));
    }
}
