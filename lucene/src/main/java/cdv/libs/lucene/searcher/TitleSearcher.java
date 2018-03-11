package cdv.libs.lucene.searcher;

import cdv.libs.lucene.analyzer.TitleAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

/**
 * Provides indexing and text searching facilities for books by title
 *
 * @author Dmitry Kulga
 *         11.03.2018 21:35
 */
public class TitleSearcher extends BasicSearcher {

    private static final String ISBN_FIELD = "isbn";
    private static final String TITLE_FIELD = "title";

    @Override
    Analyzer getIndexingAnalyzer() {
        return new TitleAnalyzer();
    }

    @Override
    void fillDocument(Document document, String isbn, String title) {
        document.add(new TextField(TITLE_FIELD, title, Field.Store.NO));
        document.add(new StringField(ISBN_FIELD, isbn, Field.Store.YES));
    }

    @Override
    String getQueryField() {
        return TITLE_FIELD;
    }

    @Override
    String getResultField() {
        return ISBN_FIELD;
    }

}
