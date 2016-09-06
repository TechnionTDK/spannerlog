package technion.tdk.spannerlog;


import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static technion.tdk.spannerlog.Utils.checkCompilation;

public class SpouseTests {

    @Test
    public void compileSentences() {
        String splogSrc = "sentences(doc_id, sentence_index, sentence_text, tokens, lemmas, \n" +
                          "          pos_tags, ner_tags, doc_offsets, dep_types, dep_tokens) :- \n" +
                          "          articles(doc_id, content),\n" +
                          "nlp_markup<content>(sentence_index, sentence_text, tokens, pos_tags, ner_tags).";
        String edbSchema = "{\"articles\":{\"column1\":\"text\",\"column2\":\"text\"}}";
        String udfSchema = "{\"nlp_markup\":{\"sentence_index\":\"int\",\"sentence_text\":\"text\"," +
                            "\"tokens\":\"text[]\",\"pos_tags\":\"text[]\",\"ner_tags\":\"text[]\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema, false));
    }

    @Test
    public void applySpanInIDBAtomShouldSucceed() {
        String splogSrc = "sentences(doc_id, sentence_index, sentence_text) :- \n" +
                          "    articles(doc_id, s), ssplit<s>(sentence_index, sentence_text).\n" +

                          "person_mention(sentence_text[span], doc_id, sentence_index, span) :-\n" +
                          "    sentences(doc_id, sentence_index, sentence_text), ner<sentence_text>(span, \"PERSON\").\n";

        String edbSchema = "{\"articles\":{\"column1\":\"text\",\"column2\":\"text\"}}";

        String udfSchema = "{\"ssplit\":{\"content\":\"text\",\"sentence_index\":\"int\",\"sentence_text\":\"text\"}," +
                           "\"ner\":{\"content\":\"text\",\"span\":\"span\",\"ner_tag\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema, false));
    }
}
