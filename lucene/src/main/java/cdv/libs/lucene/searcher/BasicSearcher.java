package cdv.libs.lucene.searcher;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Provides indexing and text searching facilities for books (ISBN - title pair).
 * Subclasses defines indexing analyzers and document mapping.
 *
 * @author Dmitry Kulga
 *         11.03.2018 21:34
 */
public abstract class BasicSearcher implements AutoCloseable {

    private IndexReader reader;
    private IndexSearcher searcher;
    private final StandardAnalyzer searchAnalyzer = new StandardAnalyzer();

    public final Set<String> find(String queryString) throws ParseException, IOException {

        Query query = new QueryParser(getQueryField(), searchAnalyzer).parse(queryString);
        ScoreDoc[] documents = searcher.search(query, Integer.MAX_VALUE).scoreDocs;

        Set<String> isbns = new HashSet<>();
        for (ScoreDoc document : documents) {
            isbns.add(searcher.doc(document.doc).get(getResultField()));
        }
        return isbns;
    }

    public final void init(Map<String, String> books) throws IOException {

        Directory index = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(getIndexingAnalyzer());

        try (IndexWriter writer = new IndexWriter(index, config)) {
            for (Map.Entry<String, String> entry : books.entrySet()) {
                Document document = new Document();
                fillDocument(document, entry.getKey(), entry.getValue());
                writer.addDocument(document);
            }
        }

        reader = DirectoryReader.open(index);
        searcher = new IndexSearcher(reader);
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }

    abstract String getQueryField();

    abstract String getResultField();

    abstract Analyzer getIndexingAnalyzer();

    abstract void fillDocument(Document document, String isbn, String title);

}
