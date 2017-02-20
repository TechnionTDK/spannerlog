package technion.tdk.spannerlog;


import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertTrue;
import static technion.tdk.spannerlog.Utils.checkCompilation;

public class SpouseTests {


    @Test
    public void compilePersonMentionOld() {
        String splogSrc =
                "person_mention(content[sentence_span][ner_span], doc_id, sentence_span, ner_span) <- \n" +
                    "articles(doc_id, content),\n" +
                    "ssplit<content>(_, sentence_span)," +
                    "ner<content[sentence_span]>(ner_span, \"PERSON\").";

        String edbSchema =
                "{\"articles\":" +
                        "{" +
                            "\"column1\":\"text\"," +
                            "\"column2\":\"text\"" +
                        "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Test
    public void compileSentences() {
        String splogSrc = "sentences(doc_id, sentence, content[sentence]) <-\n" +
                "\tarticles(doc_id, content),\n" +
                "\tssplit<content>(_, sentence).";

        String edbSchema =
                "{" +
                        "\"articles\": {" +
                            "\"column1\":\"text\"," +
                            "\"column2\":\"text\"" +
                        "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, true));
    }

    @Test
    public void compilePersonMention() {
        String splogSrc =
                "person_mention(doc_id, sentence, entity) <-\n" +
                        "\tsentences(doc_id, sentence, content),\n" +
                        "\tner<content>(entity, \"PERSON\").";

        String edbSchema =
                "{" +
                        "\"articles\": {" +
                            "\"column1\":\"text\"," +
                            "\"column2\":\"text\"" +
                        "}," +
                        "\"sentences\": {" +
                            "\"column1\": \"text\"," +
                            "\"column2\": \"span\"," +
                            "\"column3\": \"text\"" +
                        "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, true));
    }

    @Test
    public void compileSpouseCandidate() {
        String splogSrc =
                "spouse_candidate(doc_id, sentence_span, person_span1, person_span2) <-" +
                        "person_mention(name1, doc_id, sentence_span, person_span1)," +
                        "person_mention(name2, doc_id, sentence_span, person_span2)," +
                        "name1 != name2," +
                        "person_span1 != person_span2.";

        String edbSchema =
                "{" +
                        "\"articles\":\n" +
                            "{\n" +
                                "\"column1\":\"text\",\n" +
                                "\"column2\":\"text\"\n" +
                            "},\n" +
                        "\"person_mention\":" +
                            "{\n" +
                                "\"column1\": \"text\",\n" +
                                "\"column2\": \"text\",\n" +
                                "\"column3\": \"span\",\n" +
                                "\"column4\": \"span\"\n" +
                            "}\n" +
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
            "   articles(doc_id, content),\n" +
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
                    "\"articles\": {" +
                        "\"doc_id\":\"text\"," +
                        "\"content\":\"text\"" +
                    "}" +
                "}";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Ignore
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

        assertTrue(checkCompilation(splogSrc, edbSchema, null, false));
    }

    @Ignore
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

    @Ignore
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
        InputStream splogSrc = BasicSyntaxTests.class.getClassLoader().getResourceAsStream("spouse-inline.splog");

        String edbSchema =
                "{\n" +
                "    \"anchor\": {\n" +
                "        \"column1\": \"int\"\n" +
                "    },\n" +
                "    \"articles\": {\n" +
                "        \"column1\": \"text\",\n" +
                "        \"column2\": \"text\"\n" +
                "    },\n" +
                "    \"articles_2\": {\n" +
                "        \"column1\": \"text\",\n" +
                "        \"column2\": \"text\"\n" +
                "    },\n" +
                "    \"articles_3\": {\n" +
                "        \"column1\": \"text\",\n" +
                "        \"column2\": \"text\"\n" +
                "    },\n" +
                "    \"spouses_dbpedia\": {\n" +
                "        \"column1\": \"text\",\n" +
                "        \"column2\": \"text\"\n" +
                "    }\n" +
                "}\n";

        assertTrue(checkCompilation(splogSrc, edbSchema, null, true));
    }
}
