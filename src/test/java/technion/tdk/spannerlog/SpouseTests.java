package technion.tdk.spannerlog;


import com.google.gson.JsonObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static technion.tdk.spannerlog.Utils.checkCompilation;
import static technion.tdk.spannerlog.Utils.compileToJson;
import static technion.tdk.spannerlog.Utils.printJsonTree;

public class SpouseTests {

    @Test
    public void compileSentences() {
        String splogSrc = "sentences(doc_id, sentence_index, sentence_text, tokens, pos_tags, ner_tags) <- \n" +
                          "          articles(doc_id, content),\n" +
                          "nlp_markup<content>(sentence_index, sentence_text, tokens, pos_tags, ner_tags).";
        String edbSchema = "{\"articles\":{\"column1\":\"text\",\"column2\":\"text\"}}";
        String udfSchema = "{\"nlp_markup\":{\"content\":\"text\",\"sentence_index\":\"int\",\"sentence_text\":\"text\"," +
                            "\"tokens\":\"text[]\",\"pos_tags\":\"text[]\",\"ner_tags\":\"text[]\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema, false));
    }

    @Test
    public void applySpanInIDBAtomShouldSucceed() {
        String splogSrc = "sentences(doc_id, sentence_index, sentence_text) <- \n" +
                          "    articles(doc_id, s), ssplit<s>(sentence_index, sentence_text).\n" +

                          "person_mention(sentence_text[span], doc_id, sentence_index, span) <-\n" +
                          "    sentences(doc_id, sentence_index, sentence_text), ner<sentence_text>(span, \"PERSON\").\n";

        String edbSchema = "{\"articles\":{\"column1\":\"text\",\"column2\":\"text\"}}";

        String udfSchema = "{\"ssplit\":{\"content\":\"text\",\"sentence_index\":\"int\",\"sentence_text\":\"text\"}," +
                           "\"ner\":{\"content\":\"text\",\"span\":\"span\",\"ner_tag\":\"text\"}}";

        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema, false));
    }

    @Test
    public void compileProgramWithCondition() {
        String splogSrc =
                "spouse_candidate(doc_id, sentence_span, person_span1, person_span2) <-\n" +
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

//        JsonObject jsonTree = compileToJson(splogSrc, edbSchema, null);
//        printJsonTree(jsonTree);

    }

    @Test
    public void compileProgramWithFunctionOnVariables() {
        String splogSrc =
            "spouse_label(doc_id, sentence_span, person_span1, person_span2, 1, \"from_dbpedia\") <-\n" +
            "   spouse_candidate(doc_id, sentence_span, person_span1, person_span2),\n" +
            "   spouses_dbpedia(n1, n2),\n" +
            "   articles_prep(doc_id, content),\n" +
            "   lower(n1) = lower(content[sentence_span][person_span1]), lower(n2) = lower(content[sentence_span][person_span2]).";

        String edbSchema =
                "{" +
                        "\"spouse_candidate\": {" +
                            "\"doc_id\":\"text\"," +
                            "\"sentence\":\"span\"," +
                            "\"person1\":\"span\"," +
                            "\"person2\":\"span\"" +
                        "}," +
                        "\"spouses_dbpedia\": {" +
                            "\"name1\":\"text\"," +
                            "\"name2\":\"text\"" +
                        "}," +
                        "\"articles_prep\": {" +
                            "\"doc_id\":\"text\"," +
                            "\"content\":\"text\"" +
                        "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test
    public void compileProgramWithLabelRule() {
        String splogSrc =
                "spouse_label(doc_id, sentence_span, person_span1, person_span2, -1, \"neg:far_apart\") <-\n" +
                "   spouse_candidate(doc_id, sentence_span, person_span1, person_span2),\n" +
                "   articles_prep(doc_id, content),\n" +
                "   <content[sentence_span]>\\[.* x{ .* } .* y{ .* } .* ]\\,\n" +
                "   content[sentence_span][person_span1] = content[sentence_span][x],\n" +
                "   content[sentence_span][person_span2] = content[sentence_span][y],\n" +
                "   x - y > 60.";

        String edbSchema =
                "{" +
                        "\"spouse_candidate\": {" +
                            "\"doc_id\":\"text\"," +
                            "\"sentence\":\"span\"," +
                            "\"person1\":\"span\"," +
                            "\"person2\":\"span\"" +
                        "}," +
                        "\"articles_prep\": {" +
                            "\"doc_id\":\"text\"," +
                            "\"content\":\"text\"" +
                        "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, true));
    }
}
