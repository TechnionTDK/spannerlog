package technion.tdk.spannerlog;

import technion.tdk.spannerlog.rgx.Rgx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class SpannerlogDesugarRewriter {

    Map<String, String> derive(Program program) {

        List<Regex> RegexList = program.getStatements()
                .stream()
                .filter(stmt -> stmt instanceof ConjunctiveQuery)
                .map(stmt -> (ConjunctiveQuery) stmt)
                .flatMap(cq -> cq.getBody().getBodyAtoms().stream())
                .filter(atom -> atom instanceof Regex)
                .map(atom -> (Regex) atom)
                .collect(Collectors.toList());

        Map<String, String> rgxMap = new HashMap<>();

        int cnt = 1;
        for (Regex regex : RegexList) {
            String name = "rgx_gen_" + (cnt++);
            regex.setSchemaName(name);

            Rgx rgx = new Rgx(regex.getRegexString());
            regex.getTerms().addAll(rgx.getCaptureVars()
                    .stream()
                    .sorted((c1, c2) -> String.CASE_INSENSITIVE_ORDER.compare(c1.getName(), c2.getName()))
                    .map(captureVar -> new VarTerm(captureVar.getName()))
                    .collect(Collectors.toList())
            );

            rgxMap.put(name, rgx.getRegexCompiled());

        }

        return rgxMap;
    }
}
