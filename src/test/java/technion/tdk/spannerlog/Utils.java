package technion.tdk.spannerlog;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;


class Utils {
    static boolean checkCompilation(String splogSrc, String edbSchema, String udfSchema, boolean skipExport) {
        try {

            InputStream programInputStream = new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8));
            Reader edbReader = !StringUtils.isEmpty(edbSchema) ? new StringReader(edbSchema) : null;
            Reader udfReader = !StringUtils.isEmpty(udfSchema) ? new StringReader(udfSchema) : null;

            new Spannerlog().init(programInputStream, edbReader, udfReader, skipExport);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
