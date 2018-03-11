package cdv.libs.lucene.searcher;

import cdv.libs.lucene.analyzer.IsbnAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

/**
 * Provides indexing and text searching facilities for books by ISBN
 *
 * @author Dmitry Kulga
 *         11.03.2018 21:34
 */
public class IsbnSearcher extends BasicSearcher {

    private static final String ISBN_FIELD = "isbn";

    @Override
    Analyzer getIndexingAnalyzer() {
        return new IsbnAnalyzer();
    }

    @Override
    void fillDocument(Document document, String isbn, String title) {
        document.add(new TextField(ISBN_FIELD, isbn, Field.Store.YES));
    }

    @Override
    String getQueryField() {
        return ISBN_FIELD;
    }

    @Override
    String getResultField() {
        return ISBN_FIELD;
    }

}
