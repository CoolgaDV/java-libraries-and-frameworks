package cdv.libs.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.standard.StandardFilter;

/**
 * Analyzer for ISBN index that uses n-gram tokenizer.
 * So ISBN can be found by any substring.
 *
 * @author Dmitry Kulga
 *         11.03.2018 21:33
 */
public class IsbnAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String field) {
        Tokenizer source = new NGramTokenizer(1, 50);
        return new TokenStreamComponents(source, new StandardFilter(source));
    }

}
