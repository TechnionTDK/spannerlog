package technion.tdk.spannerlog;


import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static technion.tdk.spannerlog.Utils.checkCompilation;

public class SpouseTests {

    @Test
    public void compileSentences() {
        String splogSrc = "sentences(doc_id, sentence_index, sentence_text, tokens, pos_tags, ner_tags) :- \n" +
                          "          articles(doc_id, content),\n" +
                          "nlp_markup<content>(sentence_index, sentence_text, tokens, pos_tags, ner_tags).";
        String edbSchema = "{\"articles\":{\"column1\":\"text\",\"column2\":\"text\"}}";
        String udfSchema = "{\"nlp_markup\":{\"content\":\"text\",\"sentence_index\":\"int\",\"sentence_text\":\"text\"," +
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

    @Test
    public void compileProgramWithCondition() {
        String splogSrc =
                "spouse_candidate(doc_id, sentence_span, person_span1, person_span2) :-\n" +
                "   person_mention(name1, doc_id, sentence_span, person_span1),\n" +
                "   person_mention(name2, doc_id, sentence_span, person_span2),\n" +
                "   name1 != name2,\n" +
                "   person_span1 != person_span2.";

        String edbSchema =
                "{" +
                        "\"person_mention\": {" +
                            "\"name\":\"text\"," +
                            "\"doc_id\":\"text\"," +
                            "\"sentence\":\"span\"," +
                            "\"person\":\"span\"" +
                        "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }
}
