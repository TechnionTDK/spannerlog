package technion.tdk.spannerlog;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;


class Utils {
    static void printJsonTree(JsonObject jsonTree) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .serializeNulls()
                .create();

        System.out.println(gson.toJson(jsonTree));
    }

    static boolean checkCompilation(InputStream inputStream, String edbSchema, String udfSchema, boolean print) {
        JsonObject jsonTree;

        try {

            Reader edbReader = !StringUtils.isEmpty(edbSchema) ? new StringReader(edbSchema) : null;
            Reader udfReader = !StringUtils.isEmpty(udfSchema) ? new StringReader(udfSchema) : null;

            jsonTree = new Spannerlog().init(inputStream, edbReader, udfReader);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (print)
            printJsonTree(jsonTree);

        return true;
    }

    static boolean checkCompilation(String splogSrc, String edbSchema, String udfSchema, boolean print) {
        return checkCompilation(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)), edbSchema, udfSchema, print);
    }

    static JsonObject compileToJson(String splogSrc, String edbSchema, String udfSchema) {
        JsonObject jsonTree;

        try {

            InputStream programInputStream = new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8));
            Reader edbReader = !StringUtils.isEmpty(edbSchema) ? new StringReader(edbSchema) : null;
            Reader udfReader = !StringUtils.isEmpty(udfSchema) ? new StringReader(udfSchema) : null;

            jsonTree = new Spannerlog().init(programInputStream, edbReader, udfReader);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return jsonTree;
    }
}
