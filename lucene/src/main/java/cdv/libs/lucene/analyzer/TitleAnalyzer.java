package cdv.libs.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * Analyzer for book title index that uses edge n-gram token filter.
 * So book title can be found by any leading substring of every word.
 *
 * @author Dmitry Kulga
 *         11.03.2018 21:34
 */
public class TitleAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String field) {
        Tokenizer source = new StandardTokenizer();
        TokenStream stream = new EdgeNGramTokenFilter(new LowerCaseFilter(source), 1, 50);
        return new TokenStreamComponents(source, stream);
    }

}
