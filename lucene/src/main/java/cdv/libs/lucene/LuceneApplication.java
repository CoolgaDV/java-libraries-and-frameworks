package cdv.libs.lucene;

import cdv.libs.lucene.searcher.IsbnSearcher;
import cdv.libs.lucene.searcher.TitleSearcher;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Sample application for text search using Apache Lucene.
 * A couple of IT books (ISBN - title pair) are used as a data sample.
 * Two search indices are used (by ISBN and title).
 *
 * @author Dmitry Kulga
 *         11.03.2018 21:32
 */
public class LuceneApplication {

    public static void main(String[] args) throws IOException, ParseException {

        Map<String, String> codes = getBooks();

        try (IsbnSearcher isbnSearcher = new IsbnSearcher();
             TitleSearcher titleSearcher = new TitleSearcher()) {

            isbnSearcher.init(codes);
            titleSearcher.init(codes);

            System.out.println(isbnSearcher.find("07"));
            System.out.println(isbnSearcher.find("35"));

            System.out.println(titleSearcher.find("code"));
            System.out.println(titleSearcher.find("Soft"));
            System.out.println(titleSearcher.find("PROG"));
        }
    }

    private static Map<String, String> getBooks() {
        Map<String, String> books = new HashMap<>();
        books.put("0735619670", "Code Complete: A Practical Handbook of Software Construction");
        books.put("0132350882", "Clean Code: A Handbook of Agile Software Craftsmanship");
        books.put("020161622X", "The Pragmatic Programmer: From Journeyman to Master");
        return books;
    }

}
