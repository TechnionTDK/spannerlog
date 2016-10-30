package technion.tdk.spannerlog;


import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertTrue;
import static technion.tdk.spannerlog.Utils.checkCompilation;

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
            "   n1.equalsIgnoreCase(content[sentence_span][person_span1]),\n" +
            "   n2.equalsIgnoreCase(content[sentence_span][person_span2]).";

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
    public void compileProgramWithLabelRule1() {
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

    @Test
    public void compileProgramWithLabelRule2() {
        String splogSrc =
                "spouse_label(doc_id, sentence_span, person_span1, person_span2, -1, \"neg:third_person_between\") <-\n" +
                        "\tspouse_candidate(doc_id, sentence_span, person_span1, person_span2),\n" +
                        "\tarticles_prep(doc_id, content),\n" +
                        "\tperson_mention(third_person, doc_id, sentence_span, third_person_span),\n" +
                        "\t<content[sentence_span]>\\[.* x{ .* } .* z{ .* } .* y{ .* } .* ]\\,\n" +
                        "\tcontent[sentence_span][person_span1] = content[sentence_span][x],\n" +
                        "\tcontent[sentence_span][person_span2] = content[sentence_span][y],\n" +
                        "\tthird_person = content[sentence_span][z].";

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
                    "}," +
                    "\"person_mention\": {" +
                        "\"name\":\"text\"," +
                        "\"doc_id\":\"text\"," +
                        "\"sentence\":\"span\"," +
                        "\"person\":\"span\"" +
                    "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test
    public void compileProgramWithLabelRule3() {
        String splogSrc =
                "spouse_label(doc_id, sentence_span, person_span1, person_span2, 1, \"pos:wife_husband_between\") <-\n" +
                        "\tspouse_candidate(doc_id, sentence_span, person_span1, person_span2),\n" +
                        "\tarticles_prep(doc_id, content),\n" +
                        "\t@materialized <content[sentence_span]>\\[x{ .* } \\s (wife|husband) \\s y{ .* }]\\,\n" +
                        "\tx.contains(person_span1),\n" +
                        "\ty.contains(person_span2).";

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
                        "}," +
                        "\"person_mention\": {" +
                        "\"name\":\"text\"," +
                        "\"doc_id\":\"text\"," +
                        "\"sentence\":\"span\"," +
                        "\"person\":\"span\"" +
                        "}" +
                        "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test
    public void compileAggregationFunction() {
        String splogSrc =
                "spouse_label_resolved(doc_id, sentence_span, person_span1, person_span2, SUM(vote)) <-\n" +
                "   spouse_label(doc_id, sentence_span, person_span1, person_span2, vote, rule_id).";
        String edbSchema =
                "{" +
                    "\"spouse_label\": {" +
                        "\"doc_id\":\"text\"," +
                        "\"sentence\":\"span\"," +
                        "\"person1\":\"span\"," +
                        "\"person2\":\"span\"," +
                        "\"vote\":\"int\"," +
                        "\"rule_id\":\"text\"" +
                    "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test
    public void compileIfStatement() {
        String splogSrc =
                "has_spouse(doc_id, sentence_span, person_span1, person_span2) = {" +
                "   POS: l > 0;\n" +
                "   NEG: l < 0\n" +
                "   } <- spouse_label_resolved(doc_id, sentence_span, person_span1, person_span2, l).";

        String edbSchema =
                "{" +
                    "\"spouse_label_resolved\": {" +
                        "\"doc_id\":\"text\"," +
                        "\"sentence\":\"span\"," +
                        "\"person1\":\"span\"," +
                        "\"person2\":\"span\"," +
                        "\"votes\":\"int\"" +
                    "}" +
                 "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test
    public void compileInferenceRule() {
        String splogSrc =
                "has_spouse(doc_id, sentence_span, person_span1, person_span2) <- spouse_label_resolved(doc_id, sentence_span, person_span1, person_span2, l).\n" +
                "4 * [has_spouse(doc_id, sentence_span, person_span1, person_span2) => has_spouse(doc_id, sentence_span, person_span2, person_span1) ] <-\n" +
                "    spouse_candidate(doc_id, sentence_span, person_span1, person_span2).";

        String edbSchema =
                "{" +
                    "\"spouse_label_resolved\": {" +
                        "\"doc_id\":\"text\"," +
                        "\"sentence\":\"span\"," +
                        "\"person1\":\"span\"," +
                        "\"person2\":\"span\"," +
                        "\"votes\":\"int\"" +
                    "}," +
                    "\"spouse_candidate\": {" +
                        "\"doc_id\":\"text\"," +
                        "\"sentence\":\"span\"," +
                        "\"person1\":\"span\"," +
                        "\"person2\":\"span\"" +
                    "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test
    public void compileEntireProgram() {
        InputStream splogSrc = SyntaxTests.class.getClassLoader().getResourceAsStream("spouse.splog");

        String edbSchema =
                "{\n" +
                "    \"articles\": {\n" +
                "        \"column1\": \"text\",\n" +
                "        \"column2\": \"text\"\n" +
                "    },\n" +
                "    \"spouses_dbpedia\": {\n" +
                "        \"column1\": \"text\",\n" +
                "        \"column2\": \"text\"\n" +
                "    }\n" +
                "}\n";

        String udfSchema =
                "{\n" +
                "    \"ner\": {\n" +
                "        \"content\": \"text\",\n" +
                "        \"span\": \"span\",\n" +
                "        \"ner_tag\": \"text\"\n" +
                "    },\n" +
                "    \"ssplit\": {\n" +
                "        \"content\": \"text\",\n" +
                "        \"sentence_index\": \"int\",\n" +
                "        \"sentence_span\": \"span\"\n" +
                "    }\n" +
                "}\n";



        assertTrue(checkCompilation(splogSrc, edbSchema, udfSchema, true));
    }
}
