package technion.tdk.spannerlog;

import technion.tdk.spannerlog.rgx.Rgx;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class SpannerlogDesugarRewriter {

   void derive(Program program) {

        List<Regex> RegexList = program.getStatements()
                .stream()
                .filter(stmt -> stmt instanceof RuleWithConjunctiveQuery)
                .map(stmt -> (RuleWithConjunctiveQuery) stmt)
                .flatMap(cq -> cq.getBody().getBodyElements().stream())
                .filter(atom -> atom instanceof Regex)
                .map(atom -> (Regex) atom)
                .collect(Collectors.toList());

        int cnt = 1;
        for (Regex regex : RegexList) {
            String name = "rgx" + (cnt++);
            regex.setSchemaName(name);

            Rgx rgx = new Rgx(regex.getRegexString());
            regex.getTerms().addAll(rgx.getCaptureVars()
                    .stream()
                    .sorted((c1, c2) -> String.CASE_INSENSITIVE_ORDER.compare(c1.getName(), c2.getName()))
                    .map(captureVar -> new VarTerm(captureVar.getName(), Collections.emptyList()))
                    .collect(Collectors.toList())
            );

            regex.setCompiledRegexString(rgx.getRegexCompiled());
        }
    }
}
